package com.wildcodeschool.webook.Auth.domain.service;

import com.mailjet.client.errors.MailjetException;
import com.wildcodeschool.webook.Auth.domain.entity.PasswordToken;
import com.wildcodeschool.webook.Auth.domain.entity.User;
import com.wildcodeschool.webook.Auth.domain.service.interfaces.IEmailService;
import com.wildcodeschool.webook.Auth.domain.service.interfaces.IPasswordForgottenService;
import com.wildcodeschool.webook.Auth.infrastructure.exception.NotFoundException;
import com.wildcodeschool.webook.Auth.infrastructure.exception.PasswordTokenException;
import com.wildcodeschool.webook.Auth.infrastructure.repository.PasswordTokenRepository;
import com.wildcodeschool.webook.Auth.infrastructure.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class PasswordForgottenService implements IPasswordForgottenService {

    private final UserRepository userRepository;
    private final PasswordTokenRepository pwTokenRepository;
    private final IEmailService emailService;
    private final BCryptPasswordEncoder bcryptPwEncoder;

    PasswordForgottenService(UserRepository userRepository, PasswordTokenRepository pwTokenRepository,
                             IEmailService emailService, BCryptPasswordEncoder bcryptPwEncoder) {
        this.userRepository = userRepository;
        this.pwTokenRepository = pwTokenRepository;
        this.emailService = emailService;
        this.bcryptPwEncoder = bcryptPwEncoder;
    }

    @Override
    public String tokenGenerator(String email) throws MailjetException {
        User user = this.userRepository.findByEmail(email);
        if (user == null) {
            throw new NotFoundException();
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        String token = tokenProvider();
        PasswordToken passwordToken = new PasswordToken();
        passwordToken.setToken(token);
        passwordToken.setDateOfExpiration(localDateTime.plusMinutes(30)); // token s'expire dans 30 minutes
        passwordToken.setUser(user);
        passwordToken.setChanged(false);

        pwTokenRepository.save(passwordToken);
        userRepository.save(user); // user didn't change !! why save it ?
        mailSender(user, token);
        return token;
    }

    @Override
    public String tokenProvider() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[100 * 3 / 4];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder()
                .withoutPadding() // without spaces avoiding url errors
                .encodeToString(bytes);
    }

    @Override
    public void mailSender(User user, String token) throws MailjetException {
        emailService.sendMail(user.getEmail(), "Vous nous avez indiqué avoir oublié votre mot de passe." +
                " Si c'est vraiment le cas, cliquez ce lien pour en choisir un nouveau : http://localhost:4200/password-forgotten/" + token);
    }

    @Override
    public void ckeckTokenValidityAndCreateNewPassword(String token, User userNewPw) {
        PasswordToken pwToken = pwTokenRepository.findByToken(token);
        if (pwToken == null || LocalDateTime.now().isAfter(pwToken.getDateOfExpiration()) || Boolean.TRUE.equals(pwToken.getChanged())) {
            throw new PasswordTokenException();
        }
        User tokenUser = pwToken.getUser();
        String hashedNewPassword = bcryptPwEncoder.encode(userNewPw.getPassword());
        tokenUser.setPassword(hashedNewPassword);
        pwToken.setChanged(true);
        pwTokenRepository.save(pwToken);
        userRepository.save(tokenUser);
    }
}
