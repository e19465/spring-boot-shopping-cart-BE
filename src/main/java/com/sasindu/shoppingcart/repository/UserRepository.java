package com.sasindu.shoppingcart.repository;

import com.sasindu.shoppingcart.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long> {

    /**
     * Check if a user exists by email
     *
     * @param email the email of the user
     * @return true if the user exists, false otherwise
     */
    boolean existsByEmail(String email);


    /**
     * Find a user by email
     *
     * @param email the email of the user
     * @return the user
     */
    AppUser findByEmail(String email);
}
