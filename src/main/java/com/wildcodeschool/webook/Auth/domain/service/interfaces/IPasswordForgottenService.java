package com.wildcodeschool.webook.Auth.domain.service.interfaces;

import com.mailjet.client.errors.MailjetException;
import com.wildcodeschool.webook.Auth.domain.entity.User;

public interface IPasswordForgottenService {
    // classes
    public String tokenGenerator(String email) throws MailjetException;
    public String tokenProvider();
    public void mailSender(User user, String token) throws MailjetException;
    public void ckeckTokenValidityAndCreateNewPassword(String token, User userNewPw);

}
