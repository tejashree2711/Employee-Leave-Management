package com.example.leavemanagement.repository;

import com.example.leavemanagement.model.Leaves;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRepo extends JpaRepository<Leaves, Integer> {

    void deleteByStatus(String status);

    @Query(
            value = "SELECT * FROM leaves WHERE emp_id = :emp_id",
            countQuery = "SELECT count(*) FROM leaves WHERE emp_id = :emp_id",
            nativeQuery = true
    )
    Page<Leaves> findAllByEmpIdAndPageable(Integer emp_id, Pageable pageable);

    @Query(value = "select * from leaves where emp_id=:emp_id  and status=:status",nativeQuery = true)
    List<Leaves> findByStatusAndEmpId(String status, int emp_id);
}
