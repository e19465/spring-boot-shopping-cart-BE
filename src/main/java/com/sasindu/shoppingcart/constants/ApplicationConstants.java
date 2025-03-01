package com.sasindu.shoppingcart.constants;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public final class ApplicationConstants {
    public static final String IMAGE_DOWNLOAD_URL_PREFIX = "/api/v1/images/image/download/";
    public static final int MAXIMUM_ORDER_CANCEL_DAYS = 3;
    
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
    private static List<String> CORS_ALLOWED_ORIGINS;
    private static List<String> CORS_ALLOWED_METHODS;
    private static List<String> CORS_ALLOWED_HEADERS;
    private static boolean CORS_ALLOW_CREDENTIALS;

    @Value("${cors.allowed.origins}")
    private String corsAllowedOriginsString;

    @Value("${cors.allowed.methods}")
    private String corsAllowedMethodsString;

    @Value("${cors.allowed.headers}")
    private String corsAllowedHeadersString;

    @Value("${cors.allow.credentials}")
    private String corsAllowCredentialsString;

    @PostConstruct
    public void init() {
        CORS_ALLOWED_ORIGINS = Arrays.asList(corsAllowedOriginsString.split(","));
        CORS_ALLOWED_METHODS = Arrays.asList(corsAllowedMethodsString.split(","));
        CORS_ALLOWED_HEADERS = Arrays.asList(corsAllowedHeadersString.split(","));
        CORS_ALLOW_CREDENTIALS = Boolean.parseBoolean(corsAllowCredentialsString);
    }

    public static List<String> getCorsAllowedOrigins() {
        return CORS_ALLOWED_ORIGINS;
    }

    public static List<String> getCorsAllowedMethods() {
        return CORS_ALLOWED_METHODS;
    }

    public static List<String> getCorsAllowedHeaders() {
        return CORS_ALLOWED_HEADERS;
    }

    public static boolean isCorsAllowCredentials() {
        return CORS_ALLOW_CREDENTIALS;
    }
}
