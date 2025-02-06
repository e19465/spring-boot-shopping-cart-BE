package com.sasindu.shoppingcart.controllers;


import com.sasindu.shoppingcart.abstractions.dto.request.user.AddUserRequestDto;
import com.sasindu.shoppingcart.abstractions.dto.response.user.UserResponseDto;
import com.sasindu.shoppingcart.abstractions.interfaces.IAuthService;
import com.sasindu.shoppingcart.constants.ApplicationConstants;
import com.sasindu.shoppingcart.helpers.ApiResponse;
import com.sasindu.shoppingcart.helpers.GlobalExceptionHandler;
import com.sasindu.shoppingcart.helpers.GlobalSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApplicationConstants.API_URL_PREFIX + "/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IAuthService _authService;


    /**
     * Register a new user
     *
     * @param request - AddUserRequest object containing the user details
     * @return User object containing the user details
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody AddUserRequestDto request) {
        try {
            UserResponseDto user = _authService.registerUser(request).toUserResponse();
            return GlobalSuccessHandler.handleSuccess("User registered successfully", user, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e);
        }
    }
}


/*
 * ENDPOINTS
 * 1. register - POST - http://localhost:9091/api/v1/auth/register
 */
