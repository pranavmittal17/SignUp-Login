package com.demo.springbootproject.Entity;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;



@Entity
@Table(name = "user_sessions")
@Getter
@Setter
public class UserSessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Which user owns this login session?
    @ManyToOne(fetch = FetchType.LAZY, optional = false) //@ManyToOne means many session records can point to one user.
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    // Unique identifier for this particular JWT session
    @Column(nullable = false, unique = true)
    private String tokenId;

    // When this session began
    @Column(nullable = false)
    private LocalDateTime loginAt;

    // When the JWT expires
    @Column(nullable = false)
    private LocalDateTime expiresAt;

    // Last authenticated activity for idle-timeout checks
    @Column(nullable = false)
    private LocalDateTime lastActivityAt;

    // Null means no logout has been recorded
    private LocalDateTime logoutAt;
}

