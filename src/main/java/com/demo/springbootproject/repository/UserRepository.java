package com.demo.springbootproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.demo.springbootproject.Entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUsername(String username);
}

