package com.sasindu.shoppingcart.abstractions.interfaces;


import com.sasindu.shoppingcart.abstractions.dto.request.user.UpdateUserRequestDto;
import com.sasindu.shoppingcart.models.AppUser;
import jakarta.servlet.http.HttpServletResponse;

/**
 * IUserService - All the user related services defined here
 */
public interface IUserService {

    /**
     * Get user by id
     *
     * @param id - id of the user
     * @return User
     */
    AppUser getUserById(Long id);


    /**
     * Update user
     *
     * @param request - UpdateUserRequest
     * @param userId  - id of the user
     * @return User
     */
    AppUser updateUser(UpdateUserRequestDto request, Long userId);


    /**
     * Delete user by id
     *
     * @param id - id of the user
     */
    void deleteUserById(Long id, HttpServletResponse response);
}
