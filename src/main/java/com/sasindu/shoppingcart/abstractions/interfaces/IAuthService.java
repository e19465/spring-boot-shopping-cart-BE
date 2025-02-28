package com.sasindu.shoppingcart.abstractions.interfaces;


import com.sasindu.shoppingcart.abstractions.dto.request.auth.LoginRequestDto;
import com.sasindu.shoppingcart.abstractions.dto.request.user.RegisterRequestDto;
import com.sasindu.shoppingcart.models.AppUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
    AppUser registerUser(RegisterRequestDto user);


    /**
     * Login a user
     *
     * @param request - LoginRequest object containing the user details
     */
    void loginUser(LoginRequestDto request, HttpServletResponse response);


    /**
     * Get the authenticated user
     *
     * @return AppUser object
     */
    AppUser getAuthenticatedUser();


    /**
     * Refresh the tokens
     *
     * @param request  - HttpServletRequest object
     * @param response - HttpServletResponse object
     */
    void refreshTokens(HttpServletRequest request, HttpServletResponse response);


    /**
     * Logout
     *
     * @param response - HttpServletResponse object
     */
    void logout(HttpServletResponse response);


    /**
     * Check the logged-in user id from token with the given id
     *
     * @param id - Long
     * @return true if the logged-in user id is equal to the given id else false
     */
    boolean checkLoggedInUserWithId(Long id);


    /**
     * Check if the password is correct
     *
     * @param user     - AppUser object
     * @param password - String password
     * @return true if the password is correct else false
     */
    boolean isPasswordCorrect(AppUser user, String password);


    /**
     * Check if the authenticated user is an admin
     *
     * @return true if the authenticated user is an admin else false
     */
    boolean isAuthenticatedUserAdmin();
}
