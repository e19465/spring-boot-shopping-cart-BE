package com.sasindu.shoppingcart.exceptions;

public class ResourceAlreadyExistsException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Resource already exists.";

    // Constructor with a custom message
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }

    // Constructor with the default message
    public ResourceAlreadyExistsException() {
        super(DEFAULT_MESSAGE);
    }
}
