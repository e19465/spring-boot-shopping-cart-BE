package com.sasindu.shoppingcart.controllers;


import com.sasindu.shoppingcart.abstractions.dto.request.user.UpdateUserRequestDto;
import com.sasindu.shoppingcart.abstractions.dto.response.user.UserResponseDto;
import com.sasindu.shoppingcart.abstractions.interfaces.IUserService;
import com.sasindu.shoppingcart.helpers.ApiResponse;
import com.sasindu.shoppingcart.helpers.ErrorResponseHandler;
import com.sasindu.shoppingcart.helpers.SuccessResponseHandler;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * User Controller Responsible for handling all the API requests related to the user
 */
@RestController
@RequestMapping("${api.prefix}" + "/user")
@RequiredArgsConstructor
public class UserController {
    private final IUserService _userService;


    /**
     * Get user by id
     *
     * @param id - id of the user
     * @return User
     */
    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id) {
        try {
            UserResponseDto user = _userService.getUserById(id).toUserResponse();
            return SuccessResponseHandler.handleSuccess("User retrieved successfully", user, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return ErrorResponseHandler.handleException(e);
        }
    }


    /**
     * Update user
     *
     * @param request - UpdateUserRequest object containing the user details
     * @param userId  - id of the user
     * @return ApiResponse object containing the UserResponse object
     */
    @PutMapping("/update/{userId}")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequestDto request, @PathVariable Long userId) {
        try {
            UserResponseDto user = _userService.updateUser(request, userId).toUserResponse();
            return SuccessResponseHandler.handleSuccess("User updated successfully", user, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return ErrorResponseHandler.handleException(e);
        }
    }


    /**
     * Delete user by id
     *
     * @param id - id of the user
     * @return ApiResponse object containing the response details
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteUserById(@PathVariable Long id, HttpServletResponse response) {
        try {
            _userService.deleteUserById(id, response);
            return SuccessResponseHandler.handleSuccess("User deleted successfully", null, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return ErrorResponseHandler.handleException(e);
        }
    }

}


/*
 * ENDPOINTS
 * 1. get user by id - GET - http://localhost:9091/api/v1/user/find-by-id/{id}
 * 2. update user - PUT - http://localhost:9091/api/v1/user/update/{userId}
 * 3. delete user - DELETE - http://localhost:9091/api/v1/user/delete/{id}
 */
