package com.sasindu.shoppingcart.exceptions;

public class BadRequestException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Bad request.";

    // Constructor with a custom message
    public BadRequestException(String message) {
        super(message);
    }

    // Constructor with the default message
    public BadRequestException() {
        super(DEFAULT_MESSAGE);
    }
}
