package com.example.leavemanagement.requestmodels;

import lombok.Data;

@Data
public class EmployeeRequest {
    private int empId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String mobile_no;
    private String address;
    private String aadhar_no;
    private int p_id;
    private String p_name;
}
