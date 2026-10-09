package com.demo.springbootproject.controller;

import com.demo.springbootproject.service.UserSessionService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sessions")
public class UserSessionController {

    private final UserSessionService userSessionService;

    public UserSessionController(
            UserSessionService userSessionService) {
        this.userSessionService = userSessionService;
    }

    @GetMapping("/active-users/count")
    public long getActiveUserCount() {
        return userSessionService.getActiveUserCount();
    }
}
