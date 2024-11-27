package com.example.leavemanagement.Employee;
import com.example.leavemanagement.Dto.EmployeeDto;
import com.example.leavemanagement.common.CommonResponse;
import com.example.leavemanagement.requestmodels.EmployeeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmpController {

    @Autowired
    EmpService empService;

    @PostMapping("saveEmp")
    public CommonResponse<EmployeeDto> SaveEmp(@RequestBody EmployeeRequest employeeRequest) {
        return empService.SaveEmp(employeeRequest);
    }

    @GetMapping("getByEmpId")
    public CommonResponse<EmployeeDto> getEmpById(@RequestParam Integer empId) {
        return empService.getEmpById(empId);
    }

    @PostMapping("login")
    public CommonResponse<EmployeeDto> login(@RequestParam String email,
                                             @RequestParam String password) {
        return empService.login(email, password);
    }

    @GetMapping("getAllEmployee")
    public CommonResponse<Page<EmployeeDto>> getAllEmployee(
            @RequestParam(required = true) Integer emp_id,
            @RequestParam(required = false, defaultValue = "5") Integer pageSize,
            @RequestParam(required = false, defaultValue = "0") Integer pageNo) {
        return empService.getAllEmployee(emp_id, pageSize, pageNo);
    }

}