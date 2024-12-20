package com.wildcodeschool.webook.Auth.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RegistrationErrorAdvice {
    @ExceptionHandler(RegistrationErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String errorRegistrationHandler(RegistrationErrorException ex) {
        return ex.getMessage();
    }
}
