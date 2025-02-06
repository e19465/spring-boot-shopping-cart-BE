package com.sasindu.shoppingcart.repository;

import com.sasindu.shoppingcart.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Check if a user exists by email
     *
     * @param email the email of the user
     * @return true if the user exists, false otherwise
     */
    boolean existsByEmail(String email);
}
