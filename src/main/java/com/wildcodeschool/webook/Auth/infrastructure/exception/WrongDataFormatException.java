package com.wildcodeschool.webook.Auth.infrastructure.exception;

public class WrongDataFormatException extends RuntimeException {
    public WrongDataFormatException(String field) {
        super(field + ": wrong data format");
    }
}

