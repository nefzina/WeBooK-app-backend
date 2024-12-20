package com.wildcodeschool.webook.Auth.infrastructure.repository;

import com.wildcodeschool.webook.Auth.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUsername(String username);
}
