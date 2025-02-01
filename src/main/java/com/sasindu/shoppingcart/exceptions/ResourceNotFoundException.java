package com.sasindu.shoppingcart.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Resource not found.";

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
