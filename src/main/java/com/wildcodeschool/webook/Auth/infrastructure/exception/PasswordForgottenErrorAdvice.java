package com.wildcodeschool.webook.Auth.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class PasswordForgottenErrorAdvice {
    @ExceptionHandler(PasswordForgottenErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String passwordForgottenHandler(PasswordForgottenErrorException ex) {
        return ex.getMessage();
    }
}
