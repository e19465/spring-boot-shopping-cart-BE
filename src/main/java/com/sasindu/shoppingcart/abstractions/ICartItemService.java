package com.sasindu.shoppingcart.abstractions;

import com.sasindu.shoppingcart.models.CartItem;

public interface ICartItemService {

    /**
     * Add a product to the cart
     *
     * @param cartId    id of the cart to which the product should be added
     * @param productId id of the product to be added
     * @param quantity  quantity of the product to be added
     */
    void addItemToCart(Long cartId, Long productId, int quantity);


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
     * Save a cart item
     *
     * @param cartItem cart item to be saved
     * @return the saved cart item
     */
    CartItem saveCartItem(CartItem cartItem);


    /**
     * Delete all cart items by cart id
     *
     * @param cartId id of the cart
     */
    void deleteAllByCartId(Long cartId);
}
