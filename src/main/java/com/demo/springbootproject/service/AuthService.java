
package com.demo.springbootproject.service;

import com.demo.springbootproject.dto.LoginResponseDTO;
import com.demo.springbootproject.Entity.UserEntity;
import com.demo.springbootproject.Entity.UserSessionEntity;
import com.demo.springbootproject.repository.UserRepository;
import com.demo.springbootproject.repository.UserSessionRepository;
import com.demo.springbootproject.security.JwtService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserSessionRepository userSessionRepository;

    public AuthService(
            UserRepository userRepository,
            JwtService jwtService,
            PasswordEncoder passwordEncoder,
            UserSessionRepository userSessionRepository) {

        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userSessionRepository = userSessionRepository;
    }

    public LoginResponseDTO login(String email, String password) {

        log.debug("Login attempt received. email={}", email);

        // 1. Find user by email
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // 2. Verify password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            log.warn("Login failed. Invalid password. email={}", email);
            throw new RuntimeException("Invalid email or password");
        }

        // 3. Generate JWT
        String token = jwtService.generateTokens(user);

        // 4. Extract session details from JWT
        String tokenId = jwtService.extractTokenId(token);
        Date expirationDate = jwtService.extractExpiration(token);

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime expirationTime = expirationDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        // 5. Save the login session
        UserSessionEntity session = new UserSessionEntity();
        session.setUser(user);
        session.setTokenId(tokenId);
        session.setLoginAt(now);
        session.setExpiresAt(expirationTime);
        session.setLastActivityAt(now);

        userSessionRepository.save(session);

        log.info("Login successful. username={}", user.getUsername());

        // 6. Return JWT to the frontend
        return new LoginResponseDTO("Login successful", token);
    }

    public String logout(String token) {

        // 1. Get the unique ID of this JWT
        String tokenId = jwtService.extractTokenId(token);

        // 2. Find the corresponding session
        UserSessionEntity session = userSessionRepository
                .findByTokenId(tokenId)
                .orElseThrow(() ->
                        new RuntimeException("Session not found"));

        // 3. Mark the session as logged out
        if (session.getLogoutAt() == null) {
            session.setLogoutAt(LocalDateTime.now());
            userSessionRepository.save(session);
        }

        log.info("Logout successful. username={}",
                session.getUser().getUsername());

        return "Logout successful";
    }
}
