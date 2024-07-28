package com.wildcodeschool.webook.Auth.application;

import com.wildcodeschool.webook.Auth.domain.dto.UserDTO;
import com.wildcodeschool.webook.Auth.domain.dto.UserLoginDTO;
import com.wildcodeschool.webook.Auth.domain.entity.Token;
import com.wildcodeschool.webook.Auth.domain.service.*;
import com.wildcodeschool.webook.Auth.domain.entity.User;
import com.wildcodeschool.webook.Auth.infrastructure.exception.PasswordForgottenErrorException;
import com.wildcodeschool.webook.Auth.infrastructure.exception.RegistrationErrorException;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserRegistrationService userRegistrationService;
    private final CookieService cookieService;
    private final PasswordForgottenService pwForgottenService;

    public AuthController(UserService userService, JwtService jwtService, UserDetailsServiceImpl userDetailsService,
                          UserRegistrationService userRegistrationService, CookieService cookieService,
                          PasswordForgottenService pwForgottenService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.userRegistrationService = userRegistrationService;
        this.cookieService = cookieService;
        this.pwForgottenService = pwForgottenService;
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    // produces : renvoyer du json et pas du texte
    public ResponseEntity<?> login(@RequestBody User userBody) throws Exception {
        try {
            UserLoginDTO user = userService.login(userBody);
            Token token = jwtService.generateToken(userDetailsService.loadUserByEmail(userBody.getEmail()));

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookieService.createCookie(token).toString())
                    .body(user);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User userBody) throws RegistrationErrorException {
        try {
            UserDTO res = userRegistrationService.registration(userBody);
            return ResponseEntity.status(HttpStatus.CREATED).body(res);

        } catch (Exception e) {
            throw new RegistrationErrorException(e.getMessage());
        }
    }

    @PostMapping("/password-forgotten/{email}")
    public ResponseEntity<?> passwordForgotten(@PathVariable String email) throws RegistrationErrorException {
        try {
            String token = pwForgottenService.tokenGenerator(email);
            return ResponseEntity.status(201).body(token);

        } catch (Exception e) {
            throw new PasswordForgottenErrorException(e.getMessage());
        }
    }

    @PostMapping("/new-password/{token}")
    public ResponseEntity<?> newPassword(@PathVariable String token, @RequestBody User userNewPw) throws RegistrationErrorException {
        try {
            pwForgottenService.ckeckTokenValidityAndCreateNewPassword(token, userNewPw);
            return ResponseEntity.status(200).build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
