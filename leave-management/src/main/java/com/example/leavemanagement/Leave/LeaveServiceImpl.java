package com.example.leavemanagement.Leave;
import com.example.leavemanagement.common.EmployeeResponse;
import com.example.leavemanagement.common.LeaveResponse;
import com.example.leavemanagement.common.StatusConstant;
import com.example.leavemanagement.model.Employee;
import com.example.leavemanagement.model.Leaves;
import com.example.leavemanagement.repository.EmpRepo;
import com.example.leavemanagement.repository.LeaveRepo;
import com.example.leavemanagement.common.CommonResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    LeaveRepo leaveRepo;

    @Autowired
    EmpRepo empRepo;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public CommonResponse<Leaves> saveLeave(Leaves leave) {
        CommonResponse<Leaves> response = new CommonResponse<>();

        if (leave == null) {
            response.setSuccess(false);
            response.setResponseCode(HttpStatus.BAD_REQUEST.value());
            response.setResponseMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
            return response;
        }

        Leaves leave1 = null;

        if (leave.getLeave_id() != 0) {
            Optional<Leaves> optional = leaveRepo.findById(leave.getLeave_id());
            if (optional.isPresent()) {
                leave1 = optional.get();
            }
        }
        if (leave1 == null) {
            if (leave.getStartDate() == null || leave.getEndDate() == null || leave.getReason() == null) {
                response.setSuccess(false);
                response.setResponseCode(HttpStatus.BAD_REQUEST.value());
                response.setResponseMessage(LeaveResponse.DATE_AND_REASON_MUST_NOT_NULL);
                return response;
            }
            leave1=new Leaves();
            response.setResponseMessage(LeaveResponse.LEAVE_CREATED);

        } else {
            response.setResponseMessage(LeaveResponse.LEAVE_UPDATED);
        }
        modelMapper.map(leave,leave1);
        leaveRepo.save(leave1);

        Leaves leave2 = modelMapper.map(leave1, Leaves.class);
        response.setData(leave2);
        response.setResponseCode(HttpStatus.OK.value());
        response.setSuccess(true);
        return response;

    }

    @Override
    public CommonResponse<Page<Leaves>> getAllLeave(Integer emp_id, Integer pageNo, Integer pageSize) {

        CommonResponse<Page<Leaves>> response = new CommonResponse<>();

        if (emp_id == null) {
            response.setSuccess(false);
            response.setResponseCode(HttpStatus.BAD_REQUEST.value());
            response.setResponseMessage(EmployeeResponse.EMPLOYEE_ID_NOT_BE_NULL);
            return response;
        }

        Optional<Employee> employeeOptional = empRepo.findById(emp_id);

        if (!employeeOptional.isPresent()) {
            response.setSuccess(false);
            response.setResponseCode(HttpStatus.NOT_FOUND.value());
            response.setResponseMessage(EmployeeResponse.EMPLOYEE_FOUND);
            return response;
        }
        Employee emp = employeeOptional.get();
        Page<Leaves> all;
        Pageable pageRequest = PageRequest.of(pageNo, pageSize);

        if (emp.getP_name() != null && emp.getP_name().equalsIgnoreCase("manager")) {
            all = leaveRepo.findAll(pageRequest);
        } else {
            all =leaveRepo.findAllByEmpIdAndPageable(emp_id,pageRequest);

        }
//        new PageImpl<>(List.of(all), PageRequest.of(pageNo, pageSize), 1);

        response.setSuccess(true);
        response.setResponseCode(HttpStatus.FOUND.value());
        response.setResponseMessage(LeaveResponse.LEAVE_RETRIEVED);
        response.setData(all);

        return response;
    }

    @Override
    public CommonResponse<Leaves> getById(Integer leave_id) {
        CommonResponse<Leaves> response = new CommonResponse<>();
        if (leave_id == null) {
            response.setResponseCode(HttpStatus.NOT_FOUND.value());
            response.setSuccess(false);
            response.setResponseMessage(LeaveResponse.LEAVE_NOT_FOUND);
            return response;
        }
        Optional<Leaves> optional = leaveRepo.findById(leave_id);
        if (!optional.isPresent()) {
            response.setResponseCode(HttpStatus.NOT_FOUND.value());
            response.setSuccess(false);
            response.setResponseMessage(LeaveResponse.LEAVE_NOT_FOUND);
            return response;
        }
        Leaves leave = optional.get();
        response.setSuccess(true);
        response.setResponseCode(HttpStatus.FOUND.value());
        response.setResponseMessage(LeaveResponse.LEAVE_FOUND);
        response.setData(leave);
        return response;
    }

    @Override
    public CommonResponse<Leaves> deleteByStatus(Integer leave_id) {
        CommonResponse<Leaves> response = new CommonResponse<>();

        Optional<Leaves> optional = leaveRepo.findById(leave_id);

        if (!optional.isPresent()) {
            response.setResponseCode(HttpStatus.NOT_FOUND.value());
            response.setSuccess(false);
            response.setResponseMessage(LeaveResponse.LEAVE_NOT_FOUND);
            return response;
        }

        Leaves leave = optional.get();
        if (leave.getStatus().equals(StatusConstant.sent)) {
            leaveRepo.deleteByStatus(StatusConstant.sent);

            response.setSuccess(true);
            response.setResponseCode(HttpStatus.FOUND.value());
            response.setResponseMessage(LeaveResponse.LEAVE_DELETED);
            response.setData(leave);
            return response;
        } else {
            response.setResponseCode(HttpStatus.NOT_FOUND.value());
            response.setSuccess(false);
            response.setResponseMessage(LeaveResponse.SENT_LEAVE_NOT_FOUND);
            return response;

        }

    }
    @Override
    public CommonResponse<List<Leaves>> getByStatusAndEmpId(String status, Integer empId) {
        CommonResponse<List<Leaves>> response = new CommonResponse<>();

        List<Leaves> optional = leaveRepo.findByStatusAndEmpId(status, empId);
        if (optional.isEmpty()) {
            response.setResponseCode(HttpStatus.NOT_FOUND.value());
            response.setSuccess(false);
            response.setResponseMessage(LeaveResponse.LEAVE_NOT_FOUND);
            return response;
        }
        response.setSuccess(true);
        response.setResponseCode(HttpStatus.FOUND.value());
        response.setResponseMessage(LeaveResponse.LEAVE_FOUND);
        response.setData(optional);
        return response;
    }

}
