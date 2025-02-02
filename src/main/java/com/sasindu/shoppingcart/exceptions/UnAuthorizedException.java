package com.sasindu.shoppingcart.exceptions;

public class UnAuthorizedException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Unauthorized access.";

    // Constructor with a custom message
    public UnAuthorizedException(String message) {
        super(message);
    }

    // Constructor with the default message
    public UnAuthorizedException() {
        super(DEFAULT_MESSAGE);
    }
}
