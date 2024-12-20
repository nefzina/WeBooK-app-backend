package com.wildcodeschool.webook.Auth.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class PasswordTokenAdvice {
    @ExceptionHandler(PasswordTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String passwordTokenHandler(PasswordTokenException ex) {
        return ex.getMessage();
    }
}
