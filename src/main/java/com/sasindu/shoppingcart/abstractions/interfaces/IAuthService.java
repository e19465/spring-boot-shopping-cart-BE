package com.sasindu.shoppingcart.abstractions.interfaces;


import com.sasindu.shoppingcart.abstractions.dto.request.user.AddUserRequestDto;
import com.sasindu.shoppingcart.models.User;

/**
 * Interface for the AuthService
 */
public interface IAuthService {

    /**
     * Register a new user
     *
     * @param user - AddUserRequest object containing the user details
     * @return User object containing the user details
     */
    User registerUser(AddUserRequestDto user);
}
