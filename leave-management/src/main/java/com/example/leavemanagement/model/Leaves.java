package com.example.leavemanagement.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
@Data
@Entity
public class Leaves {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer leave_id;
    private Date startDate;
    private Date endDate;
    private String reason;
    private String status;
    private Integer approval_id;
    private Integer emp_id;
}
