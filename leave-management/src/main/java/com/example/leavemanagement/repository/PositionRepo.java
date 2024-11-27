package com.example.leavemanagement.repository;

import com.example.leavemanagement.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepo extends JpaRepository<Position,Integer> {
}
