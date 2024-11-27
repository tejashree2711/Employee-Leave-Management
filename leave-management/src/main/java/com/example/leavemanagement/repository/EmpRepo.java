package com.example.leavemanagement.repository;

import com.example.leavemanagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpRepo extends JpaRepository<Employee, Integer> {
    public Employee findByEmail(String email);

}
