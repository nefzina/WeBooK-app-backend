package com.wildcodeschool.webook.Auth.domain.service;

import com.wildcodeschool.webook.Auth.domain.entity.Role;
import com.wildcodeschool.webook.Auth.domain.entity.User;
import com.wildcodeschool.webook.Auth.infrastructure.exception.NotFoundException;
import com.wildcodeschool.webook.Auth.infrastructure.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getOneRole(int id) {
        return roleRepository.findById(id).orElseThrow(NotFoundException::new);
    }
}
