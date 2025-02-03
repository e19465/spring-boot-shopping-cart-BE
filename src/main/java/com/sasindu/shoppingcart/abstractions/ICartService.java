package com.sasindu.shoppingcart.abstractions;

import com.sasindu.shoppingcart.models.Cart;

import java.math.BigDecimal;

public interface ICartService {

    /**
     * Get the cart by id
     *
     * @param id The id of the cart
     * @return The cart
     */
    Cart getCartById(Long id);


    /**
     * Clear the cart
     *
     * @param id The id of the cart
     */
    void clearCart(Long id);


    /**
     * Get the total price of the cart
     *
     * @param id The id of the cart
     * @return The total price
     */
    BigDecimal getTotalPrice(Long id);


    /**
     * Save the cart
     *
     * @param cart The cart to save
     * @return The saved cart
     */
    Cart saveCart(Cart cart);
}
