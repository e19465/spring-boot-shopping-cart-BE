package com.sasindu.shoppingcart.helpers;

import com.sasindu.shoppingcart.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {

    //! Handle all exceptions
    private static ResponseEntity<ApiResponse> handleAllExceptions(HttpStatus status, Exception ex) {
        return ResponseEntity.status(status.value())
                .body(new ApiResponse(ex.getMessage(), null, null));
    }

    //! Static method to handle exceptions manually with a custom message
    public static ResponseEntity<ApiResponse> handleException(Exception e) {
        //? Bad Request Exception - Return 400
        if (e instanceof BadRequestException) {
            return handleAllExceptions(HttpStatus.BAD_REQUEST, e);
        }

        //? Unauthorized Exception - Return 401
        if (e instanceof UnAuthorizedException) {
            return handleAllExceptions(HttpStatus.UNAUTHORIZED, e);
        }

        //? Payment Required Exception - Return 402
        if (e instanceof PaymentRequiredException) {
            return handleAllExceptions(HttpStatus.PAYMENT_REQUIRED, e);
        }

        //? Forbidden Exception - Return 403
        if (e instanceof ForbiddenException) {
            return handleAllExceptions(HttpStatus.FORBIDDEN, e);
        }

        //? Not Found Exception - Return 404
        if (e instanceof NotFoundException) {
            return handleAllExceptions(HttpStatus.NOT_FOUND, e);
        }

        //? Method Not Allowed Exception - Return 405
        if (e instanceof MethodNotAllowedException) {
            return handleAllExceptions(HttpStatus.METHOD_NOT_ALLOWED, e);
        }

        //? Conflict Exception - Return 409
        if (e instanceof ConflictException) {
            return handleAllExceptions(HttpStatus.CONFLICT, e);
        }

        //? Generic Exception - Return 500
        return handleAllExceptions(HttpStatus.INTERNAL_SERVER_ERROR, e);
    }
}