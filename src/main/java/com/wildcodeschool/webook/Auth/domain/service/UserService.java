package com.wildcodeschool.webook.Auth.domain.service;

import com.wildcodeschool.webook.Auth.domain.dto.PasswordDTO;
import com.wildcodeschool.webook.Auth.domain.dto.UserDTO;
import com.wildcodeschool.webook.Auth.domain.dto.UserLoginDTO;
import com.wildcodeschool.webook.Auth.domain.entity.User;
import com.wildcodeschool.webook.Auth.infrastructure.exception.NotFoundException;
import com.wildcodeschool.webook.Auth.infrastructure.exception.WrongDataFormatException;
import com.wildcodeschool.webook.Auth.infrastructure.repository.UserRepository;

import com.wildcodeschool.webook.book.domain.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    private final UserRepository repository;
    private final BCryptPasswordEncoder bcryptPwEncoder;
    private final UserMapper userMapper;
    private final CategoryService categoryService;
    private final DataValidationService dataValidationService;

    public UserService(UserRepository repository, BCryptPasswordEncoder bcryptPwEncoder, UserMapper userMapper, CategoryService categoryService, DataValidationService dataValidationService) {
        this.repository = repository;
        this.bcryptPwEncoder = bcryptPwEncoder;
        this.userMapper = userMapper;
        this.categoryService = categoryService;
        this.dataValidationService = dataValidationService;
    }

    public List<UserDTO> getAllUsers() {
        return repository.findAll()
                .stream()
                .map(userMapper::transformUserEntityInUserDTO)
                .toList();
    }

    public UserDTO getOneUser(Long id) {
        return repository.findById(id)
                .map(userMapper::transformUserEntityInUserDTO)
                .orElseThrow(NotFoundException::new);
    }

    public UserDTO createUser(User newUser) {
        return userMapper.transformUserEntityInUserDTO(repository.save(newUser));
    }

    public UserDTO updateUser(User newUser, Long id) {
        return repository.findById(id)
                .map(user -> {
                    if (dataValidationService.UsernameValidation(newUser.getUsername())) {
                        user.setUsername(newUser.getUsername());
                    } else throw new WrongDataFormatException("Username");

                    if (newUser.getZip_code() != null) {
                        if (dataValidationService.ZipCodeValidation(newUser.getZip_code())) {
                            user.setZip_code(newUser.getZip_code());
                        } else throw new WrongDataFormatException("Zip-code");
                    }

                    user.setCity(newUser.getCity());
                    System.out.println(newUser.getProfilePicture().getFilename());
                    user.setProfilePicture(newUser.getProfilePicture());

                    if (!newUser.getPreferences().isEmpty()) {
                        user.setPreferences(newUser.getPreferences());
                    }
                    return userMapper.transformUserEntityInUserDTO(repository.save(user));
                })
                .orElseThrow(NotFoundException::new);
    }

    public HttpStatus updatePassword(PasswordDTO passwords, Long id) {
        if (!dataValidationService.PasswordValidation(passwords.newPassword()))
            throw new WrongDataFormatException("Password");
        User userData = repository.findById(id).orElseThrow(NotFoundException::new);

        if (!bcryptPwEncoder.matches(passwords.oldPassword(), userData.getPassword())) {
            throw new NotFoundException(); // ToDo : change exception
        }

        String hashedNewPassword = bcryptPwEncoder.encode(passwords.newPassword());
        userData.setPassword(hashedNewPassword);
        repository.save(userData);
        return HttpStatus.OK;
    }

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    public UserLoginDTO login(User user) {
        User userEntity = getUserEntityByEmail(user.getEmail());

        if (!bcryptPwEncoder.matches(user.getPassword(), userEntity.getPassword())) {
            throw new NotFoundException();
        }
        return userMapper.transformUserEntityInUserLoginDTO(userEntity);
    }

    public User getUserEntityByEmail(String email) {
        try {
            return repository.findByEmail(email);

        } catch (Exception e) {
            throw new NotFoundException();
        }
    }
}
