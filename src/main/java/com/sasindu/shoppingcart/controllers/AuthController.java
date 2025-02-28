package com.sasindu.shoppingcart.controllers;


import com.sasindu.shoppingcart.abstractions.dto.request.auth.LoginRequestDto;
import com.sasindu.shoppingcart.abstractions.dto.request.user.RegisterRequestDto;
import com.sasindu.shoppingcart.abstractions.dto.response.user.UserResponseDto;
import com.sasindu.shoppingcart.abstractions.interfaces.IAuthService;
import com.sasindu.shoppingcart.helpers.ApiResponse;
import com.sasindu.shoppingcart.helpers.ErrorResponseHandler;
import com.sasindu.shoppingcart.helpers.SuccessResponseHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}" + "/auth")
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
    public ResponseEntity<ApiResponse> registerUser(@RequestBody RegisterRequestDto request) {
        try {
            UserResponseDto user = _authService.registerUser(request).toUserResponse();
            return SuccessResponseHandler.handleSuccess("User registered successfully", user, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return ErrorResponseHandler.handleException(e);
        }
    }


    /**
     * Login a user
     *
     * @param request - LoginRequest object containing the user details
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> loginUser(@RequestBody LoginRequestDto request, HttpServletResponse response) {
        try {
            _authService.loginUser(request, response);
            return SuccessResponseHandler.handleSuccess("Login successful", null, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return ErrorResponseHandler.handleException(e);
        }
    }


    /**
     * Logout
     *
     * @param response - HttpServletResponse object
     */
    @GetMapping("/logout")
    public ResponseEntity<ApiResponse> logout(HttpServletResponse response) {
        try {
            _authService.logout(response);
            return SuccessResponseHandler.handleSuccess("Logout successful", null, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return ErrorResponseHandler.handleException(e);
        }
    }


    /**
     * Refresh the tokens
     *
     * @param request  - HttpServletRequest object
     * @param response - HttpServletResponse object
     */
    @GetMapping("/refresh-tokens")
    public ResponseEntity<ApiResponse> refreshTokens(HttpServletRequest request, HttpServletResponse response) {
        try {
            _authService.refreshTokens(request, response);
            return SuccessResponseHandler.handleSuccess("Tokens refreshed successfully", null, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return ErrorResponseHandler.handleException(e);
        }
    }
}


/*
 * ENDPOINTS
 * 1. register - POST - http://localhost:9091/api/v1/auth/register
 * 2. login - POST - http://localhost:9091/api/v1/auth/login
 * 3. logout - GET - http://localhost:9091/api/v1/auth/logout
 * 4. refresh-tokens - GET - http://localhost:9091/api/v1/auth/refresh-tokens
 */
