package com.example.leavemanagement.Leave;
import com.example.leavemanagement.model.Leaves;
import com.example.leavemanagement.common.CommonResponse;
import org.springframework.data.domain.Page;
import java.util.List;
public interface LeaveService {
    public CommonResponse<Leaves> saveLeave(Leaves leave);

    public CommonResponse<Page<Leaves>> getAllLeave(Integer empId, Integer pageNo, Integer pageSize);

    public CommonResponse<Leaves> getById(Integer leave_id);

    public CommonResponse<Leaves> deleteByStatus(Integer leave_id);

    public CommonResponse<List<Leaves>> getByStatusAndEmpId(String status, Integer emp_id);
}
