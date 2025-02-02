package com.sasindu.shoppingcart.exceptions;

public class ConflictException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Resource already exists.";

    // Constructor with a custom message
    public ConflictException(String message) {
        super(message);
    }

    // Constructor with the default message
    public ConflictException() {
        super(DEFAULT_MESSAGE);
    }
}
