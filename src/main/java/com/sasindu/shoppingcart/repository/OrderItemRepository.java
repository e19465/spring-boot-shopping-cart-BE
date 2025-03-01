package com.sasindu.shoppingcart.repository;

import com.sasindu.shoppingcart.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    /**
     * This method deletes all order items by order Id
     *
     * @param orderId - Id of the order
     */
    void deleteAllByOrderId(Long orderId);
}
