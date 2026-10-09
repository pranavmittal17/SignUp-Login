package com.demo.springbootproject.security;

import java.io.IOException;
import java.util.Collections;
import com.demo.springbootproject.Entity.UserSessionEntity;
import com.demo.springbootproject.repository.UserSessionRepository;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component 
public class JwtAuthenticationFilter
    extends OncePerRequestFilter {
        private final JwtService jwtService;
        private final UserSessionRepository userSessionRepository;
        @Value("${session.idle-timeout-minutes:30}")
        private long idleTimeoutMinutes;

        public JwtAuthenticationFilter(
        JwtService jwtService,
        UserSessionRepository userSessionRepository) {
                this.jwtService = jwtService;
                this.userSessionRepository = userSessionRepository;
        }
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null ||
                !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        if (jwtService.validateToken(token)) {

    String tokenId = jwtService.extractTokenId(token);

    UserSessionEntity session = userSessionRepository
            .findByTokenId(tokenId)
            .orElse(null);

    if (session != null && session.getLogoutAt() == null) {

        LocalDateTime now = LocalDateTime.now();

        boolean idleExpired = session.getLastActivityAt()
                .plusMinutes(idleTimeoutMinutes)
                .isBefore(now);

        if (!idleExpired && session.getExpiresAt().isAfter(now)) {

            session.setLastActivityAt(now);
            userSessionRepository.save(session);

            String username = jwtService.extractUsername(token);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            Collections.emptyList()
                    );

            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);
        }
    }
}
        filterChain.doFilter(request, response);
    }
}
