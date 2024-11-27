package com.example.leavemanagement.Employee;
import com.example.leavemanagement.Dto.EmployeeDto;
import com.example.leavemanagement.common.EmployeeResponse;
import com.example.leavemanagement.model.Employee;
import com.example.leavemanagement.repository.EmpRepo;
import com.example.leavemanagement.common.CommonResponse;
import com.example.leavemanagement.repository.PositionRepo;
import com.example.leavemanagement.requestmodels.EmployeeRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    EmpRepo empRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PositionRepo positionRepo;

    @Override
    public CommonResponse<EmployeeDto> SaveEmp(EmployeeRequest employeeRequest) {

        CommonResponse<EmployeeDto> response = new CommonResponse<>();

        if (employeeRequest == null || employeeRequest.getEmail() == null || employeeRequest.getPassword() == null) {
            response.setSuccess(false);
            response.setResponseCode(HttpStatus.BAD_REQUEST.value());
            response.setResponseMessage(EmployeeResponse.EMAIL_OR_PASSWORD_NOT_BE_NULL);
            return response;
        }
        Employee employee = null;

        if (employeeRequest.getEmpId() != 0) {
            Optional<Employee> employeeOptional = empRepo.findById(employeeRequest.getEmpId());
            if (employeeOptional.isPresent()) {
                employee = employeeOptional.get();
            }
        }
        if(employeeRequest.getEmail()!=null){
            Employee employee1 = empRepo.findByEmail(employeeRequest.getEmail());
            response.setSuccess(false);
            response.setResponseCode(HttpStatus.BAD_REQUEST.value());
            response.setResponseMessage(EmployeeResponse.EMAIL_ALREADY_EXIST);
            return response;
        }


        if (employee == null) {

            employee = modelMapper.map(employeeRequest, Employee.class);
            String fullname = "";
            if (employeeRequest.getFirstName() != null && employeeRequest.getLastName() != null) {
                fullname = employeeRequest.getFirstName() + employeeRequest.getLastName();
                employee.setFullname(fullname);
            }
            response.setResponseMessage(EmployeeResponse.EMPLOYEE_SAVED_SUCCESS);

        } else {

            employee = modelMapper.map(employeeRequest, Employee.class);

            String fullname = "";
            if (employeeRequest.getFirstName() != null && employeeRequest.getLastName() != null) {
                fullname = employeeRequest.getFirstName() + employeeRequest.getLastName();
                employee.setFullname(fullname);
            }

            if(employeeRequest.getEmail()!=null){
                Employee employee1 = empRepo.findByEmail(employeeRequest.getEmail());
                response.setSuccess(false);
                response.setResponseCode(HttpStatus.BAD_REQUEST.value());
                response.setResponseMessage(EmployeeResponse.EMAIL_ALREADY_EXIST);
                return response;
            }

            response.setResponseMessage(EmployeeResponse.EMPLOYEE_UPDATE_SUCCESS);
        }
        empRepo.save(employee);

        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
        response.setData(employeeDto);
        response.setResponseCode(HttpStatus.OK.value());
        response.setSuccess(true);
        return response;

    }


    @Override
    public CommonResponse<EmployeeDto> getEmpById(Integer empId) {

        CommonResponse<EmployeeDto> response = new CommonResponse<>();

        Optional<Employee> employee = empRepo.findById(empId);

        if (!employee.isPresent()) {
            response.setResponseCode(HttpStatus.NOT_FOUND.value());
            response.setSuccess(false);
            response.setResponseMessage(EmployeeResponse.EMPLOYEE_FOUND);
            return response;
        }

        Employee employee1 = employee.get();
        EmployeeDto response1 = modelMapper.map(employee1, EmployeeDto.class);
        response.setResponseCode(HttpStatus.FOUND.value());
        response.setData(employee);
        response.setSuccess(true);
        response.setResponseMessage(EmployeeResponse.EMPLOYEE_RETRIVE_SUCCESS);
        return response;
    }
    @Override
    public CommonResponse<EmployeeDto> login(String email, String password) {

        CommonResponse<EmployeeDto> response = new CommonResponse<>();

        if (email == null || password == null) {
            response.setResponseCode(HttpStatus.NOT_FOUND.value());
            response.setSuccess(false);
            response.setResponseMessage(EmployeeResponse.EMAIL_OR_PASSWORD_NOT_BE_NULL);
            return response;
        }

        Employee employee = empRepo.findByEmail(email);

        if (employee != null && employee.getEmail() != null) {

            String pass= employee.getPassword();

            if(pass==null){
                response.setResponseCode(HttpStatus.NOT_FOUND.value());
                response.setSuccess(false);
                response.setResponseMessage(EmployeeResponse.INCORRECT_PASSWORD);
                return response;
            }
            boolean val = pass.equals(password);
            if (val) {
                response.setResponseCode(HttpStatus.FOUND.value());
                response.setSuccess(true);
                response.setResponseMessage(EmployeeResponse.LOGIN_SUCCESS);
                EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
                response.setData(employeeDto);
                return response;
            } else {
                response.setResponseCode(HttpStatus.NOT_FOUND.value());
                response.setSuccess(false);
                response.setResponseMessage(EmployeeResponse.INCORRECT_PASSWORD);
                return response;
            }

        } else {
            response.setResponseCode(HttpStatus.NOT_FOUND.value());
            response.setSuccess(false);
            response.setResponseMessage(EmployeeResponse.INCORRECT_MAIL);
            return response;
        }
    }

    @Override
    public CommonResponse<Page<EmployeeDto>> getAllEmployee(Integer emp_id, Integer pageSize, Integer pageNo) {
        CommonResponse<Page<EmployeeDto>> response = new CommonResponse<>();

        if (emp_id == null) {
            response.setResponseCode(HttpStatus.BAD_REQUEST.value());
            response.setSuccess(false);
            response.setResponseMessage(EmployeeResponse.EMPLOYEE_ID_NOT_BE_NULL);
            return response;
        }

        Optional<Employee> employeeOptional = empRepo.findById(emp_id);
        if (!employeeOptional.isPresent()) {
            response.setResponseCode(HttpStatus.NOT_FOUND.value());
            response.setSuccess(false);
            response.setResponseMessage("");
            return response;
        }
        Employee employee = employeeOptional.get();
        Page<Employee> allEmp = null;

        if (employee.getP_name() != null && employee.getP_name().equalsIgnoreCase("manager")) {
            Sort sort = Sort.by(Sort.Direction.ASC, "email");
            PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
            allEmp = empRepo.findAll(pageRequest);
        }else{
            response.setResponseCode(HttpStatus.BAD_REQUEST.value());
            response.setSuccess(false);
            response.setResponseMessage(EmployeeResponse.ACCESS_DENIED);
            return response;

        }
        List<EmployeeDto> empList = allEmp.getContent()
                .stream().map(emp -> modelMapper.map(emp, EmployeeDto.class)).toList();

        Page<EmployeeDto> page = new PageImpl<>(empList, allEmp.getPageable(), allEmp.getTotalElements());

        response.setResponseCode(HttpStatus.OK.value());
        response.setSuccess(true);
        response.setResponseMessage(EmployeeResponse.EMPLOYEE_RETRIVE_SUCCESS);
        response.setData(page);
        return response;
    }
}

