package com.sasindu.shoppingcart.helpers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.nio.file.AccessDeniedException;


/**
 * GlobalExceptionHandler - Handle global exceptions
 */
@ControllerAdvice
public class GlobalExceptionHandler {


    /**
     * Handle AccessDeniedException
     *
     * @param e - Exception
     * @return ResponseEntity<ApiResponse>
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse> handleAccessDeniedException(Exception e) {
        String message = "Access denied";
        return ResponseEntity.status(HttpStatus.FORBIDDEN.value())
                .body(new ApiResponse(message, null, null));
    }


    /**
     * Handle AuthorizationDeniedException
     *
     * @param e - Exception
     * @return ResponseEntity<ApiResponse>
     */
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiResponse> handleAuthorizationDeniedException(Exception e) {
        String message = "Access denied";
        return ResponseEntity.status(HttpStatus.FORBIDDEN.value())
                .body(new ApiResponse(message, null, null));
    }


    /**
     * Handle HttpRequestMethodNotSupportedException
     *
     * @param e - Exception
     * @return ResponseEntity<ApiResponse>
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse> handleHttpRequestMethodNotSupportedException(Exception e) {
        String message = "Method not allowed";
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED.value())
                .body(new ApiResponse(message, null, null));
    }


    /**
     * Handle NoResourceFoundException
     *
     * @param e - Exception
     * @return ResponseEntity<ApiResponse>
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse> handleNoResourceFoundException(Exception e) {
        String message = "Resource not found";
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
                .body(new ApiResponse(message, null, null));
    }
}
