package com.sasindu.shoppingcart.services;

import com.sasindu.shoppingcart.abstractions.dto.request.user.AddUserRequestDto;
import com.sasindu.shoppingcart.abstractions.interfaces.IAuthService;
import com.sasindu.shoppingcart.abstractions.interfaces.ISharedService;
import com.sasindu.shoppingcart.exceptions.BadRequestException;
import com.sasindu.shoppingcart.helpers.HelperUtilStaticMethods;
import com.sasindu.shoppingcart.models.AppUser;
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
    public AppUser registerUser(AddUserRequestDto request) {
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

            if (_userRepository.existsByEmail(request.getEmail())) {
                throw new BadRequestException("Email already exists");
            }

            AppUser appUser = new AppUser();
            appUser.setFirstName(request.getFirstName());
            appUser.setLastName(request.getLastName());
            appUser.setEmail(request.getEmail());
            appUser.setPassword(request.getPassword());
            AppUser savedAppUser = _userRepository.save(appUser);

            // initialize new cart for the user
            savedAppUser.setCart(_sharedService.initializeNewCart(savedAppUser));
            return _userRepository.save(savedAppUser);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
