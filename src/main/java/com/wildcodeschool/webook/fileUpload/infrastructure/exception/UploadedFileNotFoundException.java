package com.wildcodeschool.webook.fileUpload.infrastructure.exception;

public class UploadedFileNotFoundException extends StorageException{
    public UploadedFileNotFoundException(String message) {
        super(message);
    }

    public UploadedFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
