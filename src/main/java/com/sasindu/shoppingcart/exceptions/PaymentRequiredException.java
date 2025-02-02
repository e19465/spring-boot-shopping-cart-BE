package com.sasindu.shoppingcart.exceptions;

public class PaymentRequiredException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "Payment required.";

    // Constructor with a custom message
    public PaymentRequiredException(String message) {
        super(message);
    }

    // Constructor with the default message
    public PaymentRequiredException() {
        super(DEFAULT_MESSAGE);
    }
}
