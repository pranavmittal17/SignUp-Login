package com.demo.springbootproject.Entity;
import java.time.LocalDateTime;

import com.demo.springbootproject.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter 
@Setter 
public class UserEntity {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;
    

    private String middleName;

    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank 
    private String phoneNo;



    @NotBlank
    @Email
    private String email;
    
    private String designation;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @NotBlank
    @Size(min = 12)
    private String password;

    private LocalDateTime createdAt;

  
    
}