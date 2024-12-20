package com.wildcodeschool.webook.Auth.infrastructure.repository;

import com.wildcodeschool.webook.Auth.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
