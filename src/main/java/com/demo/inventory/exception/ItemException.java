package com.demo.inventory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ItemException extends RuntimeException {

    public ItemException() {
    }

    public ItemException(String message) {
        super(message);
    }
}
