package com.sasindu.shoppingcart.services;

import com.sasindu.shoppingcart.abstractions.dto.request.auth.LoginRequestDto;
import com.sasindu.shoppingcart.abstractions.dto.request.user.RegisterRequestDto;
import com.sasindu.shoppingcart.abstractions.interfaces.IAuthService;
import com.sasindu.shoppingcart.abstractions.interfaces.ICartService;
import com.sasindu.shoppingcart.abstractions.interfaces.IUserService;
import com.sasindu.shoppingcart.exceptions.BadRequestException;
import com.sasindu.shoppingcart.exceptions.ForbiddenException;
import com.sasindu.shoppingcart.exceptions.UnAuthorizedException;
import com.sasindu.shoppingcart.helpers.HelperUtilStaticMethods;
import com.sasindu.shoppingcart.models.AppUser;
import com.sasindu.shoppingcart.security.jwt.JWTUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


/**
 * Service class for the Auth Service
 */
@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final IUserService _userService;
    private final ICartService _cartService;
    private final JWTUtils _jwtUtils;
    private final PasswordEncoder _passwordEncoder;
    private final AuthenticationManager _authenticationManager;

    @Value("${application.environment}")
    String environment;

    @Value("${jwt.access.expiration.minutes}")
    String jwtAccessExpireStringMinutes;

    @Value("${jwt.refresh.expiration.days}")
    String refreshTokenExpireStringDays;

    /**
     * Set the cookies - re-usable private method
     *
     * @param user     - AppUser object
     * @param response - HttpServletResponse object
     */
    private void setCookies(AppUser user, HttpServletResponse response, boolean isClear) {
        try {
            String access = isClear ? null : _jwtUtils.generateAccessToken(user);
            String refresh = isClear ? null : _jwtUtils.generateRefreshToken(user);
            int accessMaxAge = Integer.parseInt(jwtAccessExpireStringMinutes) * 60; // 5 minutes
            int refreshMaxAge = Integer.parseInt(refreshTokenExpireStringDays) * 24 * 60 * 60; // 7 days

            Cookie accessCookie = new Cookie("access", access);
            accessCookie.setHttpOnly(true);
            accessCookie.setSecure(environment.equals("production"));
            accessCookie.setPath("/");
            accessCookie.setMaxAge(accessMaxAge);

            Cookie refreshCookie = new Cookie("refresh", refresh);
            refreshCookie.setHttpOnly(true);
            refreshCookie.setSecure(environment.equals("production"));
            refreshCookie.setPath("/");
            refreshCookie.setMaxAge(refreshMaxAge);

            // Add the cookies to the response
            response.addCookie(accessCookie);
            response.addCookie(refreshCookie);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Register a new user
     *
     * @param request - AddUserRequest object containing the user details
     * @return User object containing the user details
     * @throws RuntimeException - if user not found
     */
    @Override
    public AppUser registerUser(RegisterRequestDto request) {
        try {
            if (!HelperUtilStaticMethods.isPasswordMatch(request.getPassword(), request.getConfirmPassword())) {
                throw new BadRequestException("Password and Confirm Password should be same");
            }

            if (!HelperUtilStaticMethods.isEmailValid(request.getEmail())) {
                throw new BadRequestException("Invalid email");
            }

            if (!HelperUtilStaticMethods.isPasswordStrong(request.getPassword())) {
                throw new BadRequestException("Password should contain at least 8 characters, 1 uppercase, 1 lowercase, 1 number and 1 special character");
            }

            if (_userService.existsByEmail(request.getEmail())) {
                throw new BadRequestException("Email already exists");
            }

            AppUser appUser = new AppUser();
            appUser.setFirstName(request.getFirstName());
            appUser.setLastName(request.getLastName());
            appUser.setEmail(request.getEmail());
            appUser.setPassword(request.getPassword());
            AppUser savedAppUser = _userService.saveUser(appUser);

            // initialize new cart for the user
            savedAppUser.setCart(_cartService.initializeNewCart(savedAppUser));
            return _userService.saveUser(savedAppUser);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Login a user
     *
     * @param request  - LoginRequest object containing the user details
     * @param response - HttpServletResponse object
     */
    @Override
    public void loginUser(LoginRequestDto request, HttpServletResponse response) {
        try {
            // Authenticate the user
            Authentication auth = _authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            // Set the authentication object to the security context
            SecurityContextHolder.getContext().setAuthentication(auth);

            // Get the authenticated user
            AppUser user = (AppUser) auth.getPrincipal();

            // Check if the user is enabled (i.e. email verified)
            if (!user.isEnabled()) {
                throw new ForbiddenException("Please verify your email to login");
            }

            // Set the cookies
            setCookies(user, response, false);
        } catch (InternalAuthenticationServiceException | BadCredentialsException e) {
            throw new UnAuthorizedException("Invalid credentials");
        } catch (DisabledException e) {
            throw new ForbiddenException("Please verify your email to login");
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * Get the authenticated user
     *
     * @return AppUser object
     */
    @Override
    public AppUser getAuthenticatedUser() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return (AppUser) auth.getPrincipal();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * Refresh the access token
     *
     * @param request  - HttpServletRequest object
     * @param response - HttpServletResponse object
     */
    @Override
    public void refreshTokens(HttpServletRequest request, HttpServletResponse response) {
        try {
            String refresh = HelperUtilStaticMethods.getCookieFromRequest(request, "refresh");

            if (!_jwtUtils.isRefreshTokenValid(refresh)) {
                throw new UnAuthorizedException("Invalid refresh token");
            }

            Long userId = _jwtUtils.getUserIdFromRefreshToken(refresh);
            AppUser user = _userService.getUserById(userId);
            if (user == null) {
                throw new ForbiddenException("Invalid refresh token");
            }
            if (!user.isEnabled()) {
                throw new ForbiddenException("Please verify your email");
            }
            setCookies(user, response, false);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * Logout - set the cookies to null
     *
     * @param response - HttpServletResponse object
     */
    @Override
    public void logout(HttpServletResponse response) {
        try {
            setCookies(null, response, true);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * Check the logged-in user id from token with the given id
     *
     * @param id - Long id
     * @return true if the logged-in user id is equal to the given id else false
     */
    @Override
    public boolean checkLoggedInUserWithId(Long id) {
        try {
            AppUser user = getAuthenticatedUser();
            return user.getId().equals(id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * Check if the password is correct
     *
     * @param user     - AppUser object
     * @param password - String password
     * @return true if the password is correct else false
     */
    @Override
    public boolean isPasswordCorrect(AppUser user, String password) {
        try {
            return _passwordEncoder.matches(password, user.getPassword());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * Check whether authenticated user is admin or not
     *
     * @return true: if admin, else return false
     */
    @Override
    public boolean isAuthenticatedUserAdmin() {
        try {
            AppUser loggedInUser = getAuthenticatedUser();
            return loggedInUser.getAuthorities()
                    .stream()
                    .anyMatch(auth -> "ROLE_ADMIN".equals(auth.getAuthority()));
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
