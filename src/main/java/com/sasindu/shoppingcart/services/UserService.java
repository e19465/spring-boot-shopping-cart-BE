package com.sasindu.shoppingcart.services;

import com.sasindu.shoppingcart.abstractions.dto.request.user.UpdateUserRequestDto;
import com.sasindu.shoppingcart.abstractions.interfaces.IAuthService;
import com.sasindu.shoppingcart.abstractions.interfaces.IUserService;
import com.sasindu.shoppingcart.exceptions.NotFoundException;
import com.sasindu.shoppingcart.exceptions.UnAuthorizedException;
import com.sasindu.shoppingcart.models.AppUser;
import com.sasindu.shoppingcart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository _userRepository;
    private final IAuthService _authService;

    /**
     * Get user by id
     *
     * @param id - id of the user
     * @return User if found, null if not found
     */
    @Override
    public AppUser getUserById(Long id) {
        try {
            if (!_authService.isAuthenticatedUserAdmin() && !_authService.checkLoggedInUserWithId(id)) {
                throw new UnAuthorizedException("Unauthorized access");
            }
            return _userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Update user
     *
     * @param request - UpdateUserRequest
     * @param userId  - id of the user
     * @return User
     * @throws RuntimeException - if user not found
     */
    @Override
    public AppUser updateUser(UpdateUserRequestDto request, Long userId) {
        try {
            return _userRepository.findById(userId)
                    .map(user -> {
                        user.setFirstName(request.getFirstName());
                        user.setLastName(request.getLastName());
                        return _userRepository.save(user);
                    })
                    .orElseThrow(() -> new NotFoundException("User not found"));
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Delete user by id
     *
     * @param id - id of the user
     * @throws RuntimeException - if user not found
     */
    @Override
    public void deleteUserById(Long id) {
        try {
            if (!_authService.isAuthenticatedUserAdmin() && !_authService.checkLoggedInUserWithId(id)) {
                throw new UnAuthorizedException("Unauthorized access");
            }
            _userRepository.findById(id)
                    .ifPresentOrElse(
                            _userRepository::delete,
                            () -> {
                                throw new RuntimeException("User not found");
                            }
                    );
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Check if the email exists
     *
     * @param email - email
     * @return boolean
     */
    @Override
    public boolean existsByEmail(String email) {
        try {
            return _userRepository.existsByEmail(email);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Save user
     *
     * @param appUser - AppUser object
     * @return AppUser object
     */
    @Override
    public AppUser saveUser(AppUser appUser) {
        try {
            return _userRepository.save(appUser);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
