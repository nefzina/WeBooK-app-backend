package com.wildcodeschool.webook.Auth.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class WrongDataFormatAdvice {
    @ResponseBody
    @ExceptionHandler(WrongDataFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String wrongDataFormatHandler(WrongDataFormatException ex) {
        return ex.getMessage();
    }
}
