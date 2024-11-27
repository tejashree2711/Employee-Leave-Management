package com.example.leavemanagement.model;
import jakarta.persistence.*;
import lombok.*;
@Data
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer empId;
    private String firstName;
    private String lastName;
    private String fullname;
    private String email;
    private String password;
    private String mobile_no;
    private String address;
    private String aadhar_no;
    private Integer p_id;
    private String p_name;
}
