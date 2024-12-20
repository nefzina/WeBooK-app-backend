package com.wildcodeschool.webook.Auth.infrastructure.repository;

import com.wildcodeschool.webook.Auth.domain.entity.PasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordTokenRepository extends JpaRepository<PasswordToken, Long> {
    PasswordToken findByToken(String token);
}
