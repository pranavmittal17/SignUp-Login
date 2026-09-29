package com.demo.springbootproject.controller;

import com.demo.springbootproject.dto.LoginRequestDTO;
import com.demo.springbootproject.dto.LoginResponseDTO;
import com.demo.springbootproject.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService; // controller to service

    // @Autowired
    // Reason?
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(
            @Valid @RequestBody LoginRequestDTO loginRequestDTO) {

        return authService.login(
            loginRequestDTO.getEmail(),
            loginRequestDTO.getPassword()
        );
    }

    @PostMapping("/logout")
    public String logout() {
        return "Logout successful";
    }
}