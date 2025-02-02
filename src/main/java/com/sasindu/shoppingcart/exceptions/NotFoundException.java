package com.sasindu.shoppingcart.exceptions;

public class NotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Resource not found.";

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
