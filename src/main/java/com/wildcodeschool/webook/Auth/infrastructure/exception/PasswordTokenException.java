package com.wildcodeschool.webook.Auth.infrastructure.exception;

public class PasswordTokenException extends RuntimeException{
    public PasswordTokenException() {
        super ("Token is not valid !");
    }

}


