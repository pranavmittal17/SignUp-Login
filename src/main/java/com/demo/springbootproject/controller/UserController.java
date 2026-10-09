package com.demo.springbootproject.controller;
import com.demo.springbootproject.dto.SignUpRequestDTO;
import com.demo.springbootproject.dto.SignUpResponseDTO;
import com.demo.springbootproject.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserController {

    private final UserService userService; // controller to service
   // private final AuthService authService;

    //QAutowired 
    public UserController(UserService userService) {
        this.userService = userService;
       // this.authService = authService;
    }

    @PostMapping("/signup")
    public SignUpResponseDTO signup(@Valid @RequestBody SignUpRequestDTO signUpRequestDTO) {
        return userService.saveUser(signUpRequestDTO);
    }

    @GetMapping("/")
    public String test() {
        return "Spring Boot is working!";
    }

    @GetMapping("/usernames")
    public List<String> getAllUsernames() {
        return userService.getAllUsernames();
    }
    

    @GetMapping("/profile")
    public SignUpResponseDTO getProfile(Authentication authentication) {
        return userService.getUserByUsername(authentication.getName());
    }

}


// @RestController
// public class TestController {

//     private final UserService userService;

//     public TestController(UserService userService) {
//         this.userService = userService;
//     }

//     @PostMapping("/signup")
//     public User signup(@Valid @RequestBody User user) {

//         return userService.saveUser(user);
//     }
// }