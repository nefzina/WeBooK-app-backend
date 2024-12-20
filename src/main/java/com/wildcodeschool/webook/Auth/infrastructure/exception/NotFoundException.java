package com.wildcodeschool.webook.Auth.infrastructure.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("Object not found");
    }
}
