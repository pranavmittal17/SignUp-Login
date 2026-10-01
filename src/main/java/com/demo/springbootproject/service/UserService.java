package com.demo.springbootproject.service;
import com.demo.springbootproject.Entity.UserEntity;
import com.demo.springbootproject.dto.SignUpRequestDTO;
import com.demo.springbootproject.dto.SignUpResponseDTO;
import com.demo.springbootproject.repository.UserRepository;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository; //service to repository

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    private String generateUsername(UserEntity user) {
    return user.getFirstName().toLowerCase()
            + "."
            + user.getEmployeeId();
}

    public SignUpResponseDTO saveUser(SignUpRequestDTO signUpRequestDTO) {
        UserEntity user = new UserEntity();
        
        String decodedPassword = new String(
        Base64.getDecoder().decode(signUpRequestDTO.getPassword()),
        StandardCharsets.UTF_8);

        if(!signUpRequestDTO.getConfirmPassword().equals(decodedPassword)){
            throw new IllegalArgumentException("Passwords do not match");        }

        String encodedPassword = passwordEncoder.encode(decodedPassword);

        user.setEmployeeId(signUpRequestDTO.getEmployeeId());
        user.setFirstName(signUpRequestDTO.getFirstName());
        user.setLastName(signUpRequestDTO.getLastName());
        user.setMiddleName(signUpRequestDTO.getMiddleName());
         
        user.setUsername(generateUsername(user));        
        user.setPhoneNo(signUpRequestDTO.getPhoneNo());
        user.setDesignation(signUpRequestDTO.getDesignation());
        user.setUserRole(signUpRequestDTO.getUserRole());
        user.setEmail(signUpRequestDTO.getEmail());
        user.setPassword(encodedPassword);        
        user.setCreatedAt(LocalDateTime.now());

        

        UserEntity savedUser = userRepository.save(user);

        return new SignUpResponseDTO(
        savedUser.getId(),
        savedUser.getEmployeeId(),
        savedUser.getFirstName(),
        savedUser.getMiddleName(),
        savedUser.getUsername(),
        savedUser.getLastName(),
        savedUser.getPhoneNo(),
        savedUser.getEmail(),
        savedUser.getDesignation(),
        savedUser.getUserRole(),
        savedUser.getCreatedAt()
);
    }
}