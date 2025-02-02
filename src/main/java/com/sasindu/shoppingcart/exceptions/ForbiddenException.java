package com.sasindu.shoppingcart.exceptions;

public class ForbiddenException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Forbidden access.";

    // Constructor with a custom message
    public ForbiddenException(String message) {
        super(message);
    }

    // Constructor with the default message
    public ForbiddenException() {
        super(DEFAULT_MESSAGE);
    }

}
