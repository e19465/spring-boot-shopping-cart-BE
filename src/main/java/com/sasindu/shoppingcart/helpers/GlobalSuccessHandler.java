package com.sasindu.shoppingcart.helpers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GlobalSuccessHandler {
    //! Handle success responses, public access
    public static ResponseEntity<ApiResponse> handleSuccess(String message, Object data, int status, HttpHeaders headers) {
        if (status == HttpStatus.OK.value()) {
            return handleAllSuccess(message, "Success", data, status, headers);
        } else if (status == HttpStatus.CREATED.value()) {
            return handleAllSuccess(message, "Created", data, status, headers);
        }
        return handleAllSuccess(message, "Success", data, HttpStatus.OK.value(), headers);
    }

    //! Handle all success responses, including headers
    private static ResponseEntity<ApiResponse> handleAllSuccess(String message, String defaultMessage, Object data, int status, HttpHeaders headers) {
        if (headers != null) {
            return ResponseEntity.status(status)
                    .headers(headers)
                    .body(new ApiResponse(null, message != null ? message : defaultMessage, data));
        } else {
            return ResponseEntity.status(status)
                    .body(new ApiResponse(null, message != null ? message : defaultMessage, data));
        }
    }
}
