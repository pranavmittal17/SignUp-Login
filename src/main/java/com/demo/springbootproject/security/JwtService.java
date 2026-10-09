package com.demo.springbootproject.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.demo.springbootproject.Entity.UserEntity;
import java.util.UUID;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

@Service
public class JwtService {
    private final SecretKey secretKey;

    public JwtService(@Value("${jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateTokens(UserEntity user) {
        Date now = new Date();
        Date expiry = new Date(System.currentTimeMillis() + 1000L * 60 * 60); // shift time to config.
        String tokenId = UUID.randomUUID().toString();
// UUID.randomUUID() generates a unique identifier for each login.

        return Jwts.builder()
                .id(tokenId) // .id(tokenId) puts that identifier into the JWT's jti claim.
                .subject(user.getUsername())
                .issuedAt(now)
                .expiration(expiry)
                .signWith(secretKey)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

// extractTokenId() retrieves it later so the backend can find the corresponding UserSessionEntity
    
    public String extractTokenId(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getId();
    }


    public Date extractExpiration(String token) {
        
    return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getExpiration();
}

//    public Long extractUserId(String token) {
//         return Jwts.parser()
//                 .verifyWith(secretKey)
//                 .build()
//                 .parseSignedClaims(token)
//                 .getPayleoad()
//                 .get("userId", Long.class);
//         }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
