package com.demo.springbootproject.controller;
import com.demo.springbootproject.dto.SignUpRequestDTO;
import com.demo.springbootproject.dto.SignUpResponseDTO;
import com.demo.springbootproject.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserController {

    private final UserService userService; // controller to service

    //QAutowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public SignUpResponseDTO signup(@Valid @RequestBody SignUpRequestDTO signUpRequestDTO) {
        return userService.saveUser(signUpRequestDTO);
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