package com.demo.springbootproject.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.springbootproject.Entity.UserSessionEntity;

public interface UserSessionRepository extends JpaRepository<UserSessionEntity, Long> {
 //token id distinguishes this login from the same user's other logins. It is not the raw JWT
 //If no record is found, we must not treat that session as active.
    Optional<UserSessionEntity> findByTokenId(String tokenId);

    @Query("""
    SELECT COUNT(DISTINCT s.user.id)
    FROM UserSessionEntity s
    WHERE s.logoutAt IS NULL
      AND s.expiresAt > :now
      AND s.lastActivityAt > :idleCutoff""")
long countActiveUsers(
        @Param("now") LocalDateTime now,
        @Param("idleCutoff") LocalDateTime idleCutoff
);
}

