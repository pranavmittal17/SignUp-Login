package com.demo.springbootproject.dto;

import com.demo.springbootproject.enums.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


//all fields have been added here 

@Getter 
@Setter 
public class SignUpRequestDTO {

    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotBlank(message = "First Name is required")
    private String firstName;

    private String middleName;

    @NotBlank(message = "Last Name is required")
    private String lastName;

    @NotBlank(message = "Phone number is required")
    @Pattern(
    regexp = "^[6-9][0-9]{9}$",
    message = "Please enter a valid 10-digit phone number")
    private String phoneNo;

    @Email
    @NotBlank(message="Official Work email is required ")
    @Pattern
    (regexp = "^[A-Za-z0-9._%+-]+@soprasteria\\.com$",
    message = "Please use your company email address")
    private String email;

    private String designation;

    private UserRole userRole;
    

    @NotBlank(message = "Password is required")
    @Size(min = 12, message = "Password must be at least 12 characters")
    private String password;

    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;

}
