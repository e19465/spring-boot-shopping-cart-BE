package com.sasindu.shoppingcart.helpers;

import com.sasindu.shoppingcart.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    //! Handle all exceptions
    @ExceptionHandler(Exception.class)
    private static ResponseEntity<ApiResponse> handleAllExceptions(int status, String message, String defaultMessage, Exception ex) {
        return ResponseEntity.status(status)
                .body(new ApiResponse(message != null ? message : defaultMessage, ex.getMessage()));
    }

    //! Static method to handle exceptions manually with a custom message
    public static ResponseEntity<ApiResponse> handleException(Exception e, String message) {
        //? Bad Request Exception - Return 400
        if (e instanceof BadRequestException) {
            return handleAllExceptions(HttpStatus.BAD_REQUEST.value(), message, "Bad Request", e);
        }

        //? Unauthorized Exception - Return 401
        if (e instanceof UnAuthorizedException) {
            return handleAllExceptions(HttpStatus.UNAUTHORIZED.value(), message, "Unauthorized", e);
        }

        //? Payment Required Exception - Return 402
        if (e instanceof PaymentRequiredException) {
            return handleAllExceptions(HttpStatus.PAYMENT_REQUIRED.value(), message, "Payment Required", e);
        }

        //? Forbidden Exception - Return 403
        if (e instanceof ForbiddenException) {
            return handleAllExceptions(HttpStatus.FORBIDDEN.value(), message, "Forbidden", e);
        }

        //? Not Found Exception - Return 404
        if (e instanceof NotFoundException) {
            return handleAllExceptions(HttpStatus.NOT_FOUND.value(), message, "Resource not found", e);
        }

        //? Method Not Allowed Exception - Return 405
        if (e instanceof MethodNotAllowedException) {
            return handleAllExceptions(HttpStatus.METHOD_NOT_ALLOWED.value(), message, "Method Not Allowed", e);
        }

        //? Conflict Exception - Return 409
        if (e instanceof ConflictException) {
            return handleAllExceptions(HttpStatus.CONFLICT.value(), message, "Conflict", e);
        }

        //? Generic Exception - Return 500
        return handleAllExceptions(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, "Internal Server Error", e);
    }
}