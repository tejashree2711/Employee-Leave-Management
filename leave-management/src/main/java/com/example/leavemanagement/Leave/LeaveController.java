package com.example.leavemanagement.Leave;

import com.example.leavemanagement.model.Leaves;
import com.example.leavemanagement.repository.LeaveRepo;
import com.example.leavemanagement.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LeaveController {

    @Autowired
    LeaveService leaveService;

    @Autowired
    LeaveRepo leaveRepo;

    @PostMapping("applyleave")
    public CommonResponse<Leaves> saveLeave(@RequestBody Leaves leave) {
        return leaveService.saveLeave(leave);
    }

    @GetMapping("getAllLeave")
    public CommonResponse<Page<Leaves>> getAllLeave(@RequestParam Integer emp_id,
                                                    @RequestParam(required = false,defaultValue = "0") Integer pageNo,
                                                    @RequestParam(required = false,defaultValue = "5") Integer pageSize) {
        return leaveService.getAllLeave(emp_id, pageNo, pageSize);
    }

    @GetMapping("getById")
    public CommonResponse<Leaves> getById(@RequestParam Integer leave_id) {
        return leaveService.getById(leave_id);
    }

    @DeleteMapping("deleteByStatus")
    public CommonResponse<Leaves> deleteByStatus(@RequestParam Integer leave_id) {
        return leaveService.deleteByStatus(leave_id);
    }

    @GetMapping("getbystatus")
    public CommonResponse<List<Leaves>> getByStatusAndEmpId(@RequestParam String status,
                                                            @RequestParam Integer empId) {
        return leaveService.getByStatusAndEmpId(status,empId);
    }

}