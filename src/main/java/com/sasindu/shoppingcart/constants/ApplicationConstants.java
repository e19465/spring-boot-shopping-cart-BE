package com.sasindu.shoppingcart.constants;

import org.springframework.beans.factory.annotation.Value;

public final class ApplicationConstants {
    public static final String IMAGE_DOWNLOAD_URL_PREFIX = "/api/v1/images/image/download/";

    //! Configure Public URLs
    private static final String[] PUBLIC_APPLICATION_URLS = new String[]{
            "/static/**",
            "/favicon.ico",
            "/error",
            "/webjars/**",
    };
    private static final String[] PUBLIC_API_SHARED_URLS = new String[]{
            "/api/v1/auth/**",
            "/api/v1/public/**",
    };
    private static final String[] PUBLIC_API_CATEGORY_URLS = new String[]{
            "/api/v1/category/find-by-id/**",
            "/api/v1/category/find-by-name/**",
            "/api/v1/category/get-all/**",
            "/api/v1/category/get-products/**",
    };
    private static final String[] PUBLIC_API_PRODUCT_URLS = new String[]{
            "/api/v1/product/find-by-id/**",
            "/api/v1/product/get-all/**",
            "/api/v1/product/filter/**",
            "/api/v1/product/count/**",
    };
    private static final String[] PUBLIC_API_IMAGE_URLS = new String[]{
            "/api/v1/images/image/download/**",
    };
    // Combine multiple arrays into one
    public static final String[] PUBLIC_URLS = combineArrays(
            PUBLIC_APPLICATION_URLS,
            PUBLIC_API_SHARED_URLS,
            PUBLIC_API_CATEGORY_URLS,
            PUBLIC_API_PRODUCT_URLS,
            PUBLIC_API_IMAGE_URLS
    );

    // Method to combine multiple arrays into one
    private static String[] combineArrays(String[]... arrays) {
        // Calculate the total length of all arrays
        int totalLength = 0;
        for (String[] array : arrays) {
            totalLength += array.length;
        }

        // Create a new array to hold all elements
        String[] combined = new String[totalLength];
        int currentIndex = 0;

        // Copy each array into the combined array
        for (String[] array : arrays) {
            System.arraycopy(array, 0, combined, currentIndex, array.length);
            currentIndex += array.length;
        }

        return combined;
    }

    //!  CORS Configuration
    private static String[] CORS_ALLOWED_ORIGINS;
    private static String[] CORS_ALLOWED_METHODS;
    private static String[] CORS_ALLOWED_HEADERS;
    private static boolean CORS_ALLOW_CREDENTIALS;

    public static String[] getCorsAllowedOrigins() {
        return CORS_ALLOWED_ORIGINS;
    }

    @Value("${cors.allowed.origins}")
    public void setCorsAllowedOrigins(String corsAllowedOriginsString) {
        CORS_ALLOWED_ORIGINS = corsAllowedOriginsString.split(",");
    }

    public static String[] getCorsAllowedMethods() {
        return CORS_ALLOWED_METHODS;
    }

    @Value("${cors.allowed.methods}")
    public void setCorsAllowedMethods(String corsAllowedMethodsString) {
        CORS_ALLOWED_METHODS = corsAllowedMethodsString.split(",");
    }

    public static String[] getCorsAllowedHeaders() {
        return CORS_ALLOWED_HEADERS;
    }

    @Value("${cors.allowed.headers}")
    public void setCorsAllowedHeaders(String corsAllowedHeadersString) {
        CORS_ALLOWED_HEADERS = corsAllowedHeadersString.split(",");
    }

    public static boolean isCorsAllowCredentials() {
        return CORS_ALLOW_CREDENTIALS;
    }

    @Value("${cors.allow.credentials}")
    public void setCorsAllowCredentials(String corsAllowCredentialsString) {
        CORS_ALLOW_CREDENTIALS = corsAllowCredentialsString.equals("true");
    }
}
