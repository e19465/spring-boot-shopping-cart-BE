package com.sasindu.shoppingcart.abstractions.interfaces;


import com.sasindu.shoppingcart.models.Order;

import java.util.List;

/**
 * The interface Order service. Defines the methods that the Order service should implement.
 */
public interface IOrderService {

    /**
     * Place order.
     *
     * @return the order
     */
    Order placeOrder();


    /**
     * Gets order.
     *
     * @param orderId the order id
     * @return the order
     */
    Order getOrderById(Long orderId);


    /**
     * Gets orders by user id.
     *
     * @param userId the user id
     * @return the orders by user id
     */
    List<Order> getOrdersByUserId(Long userId);
}
