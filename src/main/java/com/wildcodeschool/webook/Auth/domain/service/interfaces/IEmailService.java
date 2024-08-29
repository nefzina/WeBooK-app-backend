package com.wildcodeschool.webook.Auth.domain.service.interfaces;


import com.mailjet.client.errors.MailjetException;
import com.wildcodeschool.webook.Auth.domain.entity.User;

public interface IEmailService {
   void sendMail(User user, String body) throws MailjetException;
}
