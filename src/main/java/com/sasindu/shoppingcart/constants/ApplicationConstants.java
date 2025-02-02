package com.sasindu.shoppingcart.constants;

public final class ApplicationConstants {

    // Prevent instantiation
    private ApplicationConstants() {
        throw new UnsupportedOperationException("This is a constants class and cannot be instantiated");
    }

    public static final String BASE_API_URL = "/api/v1";
    public static final String IMAGE_DOWNLOAD_URL_PREFIX = "/api/v1/images/image/download/";
}
