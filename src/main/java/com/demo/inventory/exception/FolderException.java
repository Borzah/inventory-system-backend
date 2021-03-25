package com.demo.inventory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FolderException extends RuntimeException {

    public FolderException() {
    }

    public FolderException(String message) {
        super(message);
    }
}
