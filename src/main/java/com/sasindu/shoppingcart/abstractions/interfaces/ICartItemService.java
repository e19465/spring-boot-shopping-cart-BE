package com.sasindu.shoppingcart.abstractions.interfaces;

import com.sasindu.shoppingcart.models.CartItem;

import java.util.List;

public interface ICartItemService {

    /**
     * Add a product to the cart
     *
     * @param userId    id of the user to whom the product should be added
     * @param productId id of the product to be added
     * @param quantity  quantity of the product to be added
     */
    void addItemToCart(Long userId, Long productId, int quantity);


    /**
     * Remove a product from the cart
     *
     * @param cartId    id of the cart from which the product should be removed
     * @param productId id of the product to be removed
     */
    void removeItemFromCart(Long cartId, Long productId);


    /**
     * Update the quantity of a product in the cart
     *
     * @param cartId    id of the cart in which the product should be updated
     * @param productId id of the product to be updated
     * @param quantity  new quantity of the product
     */
    void updateItemQuantity(Long cartId, Long productId, int quantity);


    /**
     * Get the cart items by user id
     *
     * @param userId id of the user
     * @return list of cart items
     */
    List<CartItem> getCartItemsByUserId(Long userId);


    /**
     * Get the cart items by cart id
     *
     * @param cartId id of the cart
     * @return list of cart items
     */
    List<CartItem> getCartItemsByCartId(Long cartId, Long userId);

}
