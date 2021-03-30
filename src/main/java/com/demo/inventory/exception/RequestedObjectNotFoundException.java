package com.demo.inventory.exception;

public class RequestedObjectNotFoundException extends RuntimeException {

    public RequestedObjectNotFoundException() {
    }

    public RequestedObjectNotFoundException(String message) {
        super(message);
    }
}
