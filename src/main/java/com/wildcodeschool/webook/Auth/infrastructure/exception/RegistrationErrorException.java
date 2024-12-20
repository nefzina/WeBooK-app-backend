package com.wildcodeschool.webook.Auth.infrastructure.exception;

public class RegistrationErrorException extends RuntimeException{
    public RegistrationErrorException(String message) {
        super(message);
    }
}
