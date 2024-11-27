package com.example.leavemanagement.Employee;
import com.example.leavemanagement.Dto.EmployeeDto;
import com.example.leavemanagement.common.CommonResponse;
import com.example.leavemanagement.requestmodels.EmployeeRequest;
import org.springframework.data.domain.Page;

public interface EmpService {

    public CommonResponse<EmployeeDto> SaveEmp(EmployeeRequest employeeRequest);

    public CommonResponse<EmployeeDto> getEmpById(Integer emp_id);

    public CommonResponse<EmployeeDto> login(String email, String password);

    public CommonResponse<Page<EmployeeDto>> getAllEmployee(Integer emp_id, Integer pageSize,Integer pageNo);
}
