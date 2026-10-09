package com.demo.springbootproject.service;

import com.demo.springbootproject.repository.UserSessionRepository;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserSessionService {

    private final UserSessionRepository userSessionRepository;

    @Value("${session.idle-timeout-minutes:30}")
    private long idleTimeoutMinutes;

    public UserSessionService(
            UserSessionRepository userSessionRepository) {
        this.userSessionRepository = userSessionRepository;
    }

    public long getActiveUserCount() {

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime idleCutoff =
                now.minusMinutes(idleTimeoutMinutes);

        return userSessionRepository.countActiveUsers(
                now,
                idleCutoff
        );
    }
}
