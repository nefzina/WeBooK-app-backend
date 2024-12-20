package com.wildcodeschool.webook.Auth.infrastructure.exception;

public class PasswordForgottenErrorException extends RuntimeException{
    public PasswordForgottenErrorException(String message) {
        super (message);
    }
}
