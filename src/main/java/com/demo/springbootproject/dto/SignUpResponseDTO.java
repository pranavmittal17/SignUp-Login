package com.demo.springbootproject.dto;

import java.time.LocalDateTime;

import com.demo.springbootproject.enums.UserRole;

import lombok.Getter;
import lombok.Setter;
@Getter 
@Setter 
public class SignUpResponseDTO {

    private Long id;
    private Long employeeId;
    private LocalDateTime createdAt;
    private String firstName;
    private String lastName;
    private String middleName;
    private String username;
    private String phoneNo;
    private String email;

    private String designation;
    private UserRole userRole;


    public SignUpResponseDTO(Long id, Long employeeId,String firstName,  String middleName, String lastName, String username,String phoneNo, String email,String designation, UserRole userRole, LocalDateTime createdAt) {
        this.id = id;
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.username = username;
        this.phoneNo = phoneNo;
        this.email = email;
        this.designation= designation;
        this.userRole = userRole;
        this.createdAt  = createdAt;
    }

}