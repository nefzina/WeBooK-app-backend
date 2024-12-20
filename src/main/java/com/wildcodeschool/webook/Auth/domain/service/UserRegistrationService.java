package com.wildcodeschool.webook.Auth.domain.service;

import com.wildcodeschool.webook.Auth.domain.dto.UserDTO;
import com.wildcodeschool.webook.Auth.domain.entity.User;
import com.wildcodeschool.webook.Auth.infrastructure.exception.WrongDataFormatException;
import com.wildcodeschool.webook.Auth.infrastructure.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {
    private final UserRepository repository;
    private final BCryptPasswordEncoder bcryptPwEncoder;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final DataValidationService dataValidationService;

    public UserRegistrationService(UserRepository repository, BCryptPasswordEncoder bcryptPwEncoder,
                                   UserMapper userMapper, RoleService roleService, DataValidationService dataValidationService) {
        this.repository = repository;
        this.bcryptPwEncoder = bcryptPwEncoder;
        this.userMapper = userMapper;
        this.roleService = roleService;
        this.dataValidationService = dataValidationService;
    }

    public UserDTO registration(User userData) throws Exception {
        if (dataValidationService.PasswordValidation(userData.getPassword())) {
            userData.setPassword(bcryptPwEncoder.encode(userData.getPassword()));
        } else throw new WrongDataFormatException("Password");
        userData.setRole(roleService.getOneRole(1));
        userData.setEnabled(true);

        if (!dataValidationService.EmailValidation(userData.getEmail())) throw new WrongDataFormatException("Email");
        if (!dataValidationService.UsernameValidation(userData.getUsername()))
            throw new WrongDataFormatException("Username");

        try {
            User user = repository.save(userData);
            return userMapper.transformUserEntityInUserDTO(user);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
