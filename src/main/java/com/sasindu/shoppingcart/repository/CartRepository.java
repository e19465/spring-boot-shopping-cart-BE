package com.sasindu.shoppingcart.repository;

import com.sasindu.shoppingcart.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * The cart repository, which is used to interact with the database
 */
public interface CartRepository extends JpaRepository<Cart, Long> {
}
