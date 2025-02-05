package com.sasindu.shoppingcart.repository;

import com.sasindu.shoppingcart.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
