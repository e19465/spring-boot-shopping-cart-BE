package com.sasindu.shoppingcart.repository;

import com.sasindu.shoppingcart.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * The interface Order repository (database operations using JPA).
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Find all orders by user id.
     *
     * @param userId the user id
     * @return the list of orders
     */
    List<Order> findAllByUserId(Long userId);
}
