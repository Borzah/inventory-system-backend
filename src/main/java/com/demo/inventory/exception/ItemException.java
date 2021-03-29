package com.demo.inventory.exception;

public class ItemException extends RuntimeException {

    public ItemException() {
    }

    public ItemException(String message) {
        super(message);
    }
}
