package com.sasindu.shoppingcart.exceptions;

public class MethodNotAllowedException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Method not allowed.";

    // Constructor with a custom message
    public MethodNotAllowedException(String message) {
        super(message);
    }

    // Constructor with the default message
    public MethodNotAllowedException() {
        super(DEFAULT_MESSAGE);
    }
}
