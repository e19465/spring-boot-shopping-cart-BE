package com.sasindu.shoppingcart.services;

import com.sasindu.shoppingcart.abstractions.dto.request.user.AddUserRequestDto;
import com.sasindu.shoppingcart.abstractions.interfaces.IAuthService;
import com.sasindu.shoppingcart.abstractions.interfaces.ISharedService;
import com.sasindu.shoppingcart.exceptions.BadRequestException;
import com.sasindu.shoppingcart.helpers.Utils;
import com.sasindu.shoppingcart.models.User;
import com.sasindu.shoppingcart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * Service class for the Auth Service
 */
@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final UserRepository _userRepository;
    private final ISharedService _sharedService;

    /**
     * Register a new user
     *
     * @param request - AddUserRequest object containing the user details
     * @return User object containing the user details
     * @throws RuntimeException - if user not found
     */
    @Override
    public User registerUser(AddUserRequestDto request) {
        try {
            if (!Utils.isPasswordMatch(request.getPassword(), request.getConfirmPassword())) {
                throw new BadRequestException("Password and Confirm Password should be same");
            }

            if (!Utils.isEmailValid(request.getEmail())) {
                throw new BadRequestException("Invalid email");
            }

            if (!Utils.isPasswordStrong(request.getPassword())) {
                throw new BadRequestException("Password should contain at least 8 characters, 1 uppercase, 1 lowercase, 1 number and 1 special character");
            }

            if (_userRepository.existsByEmail(request.getEmail())) {
                throw new BadRequestException("Email already exists");
            }

            User user = new User();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            User savedUser = _userRepository.save(user);

            // initialize new cart for the user
            savedUser.setCart(_sharedService.initializeNewCart(savedUser));
            return _userRepository.save(savedUser);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
