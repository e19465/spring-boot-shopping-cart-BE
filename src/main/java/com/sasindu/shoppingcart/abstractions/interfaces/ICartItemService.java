package com.sasindu.shoppingcart.abstractions.interfaces;

import com.sasindu.shoppingcart.abstractions.dto.request.cartitem.AddCartItemRequestDto;
import com.sasindu.shoppingcart.abstractions.dto.request.cartitem.UpdateCartItemRequestDto;
import com.sasindu.shoppingcart.models.CartItem;

import java.util.List;

public interface ICartItemService {

    /**
     * Add a product to the cart
     *
     * @param request AddCartItemRequestDto object
     */
    void addItemToCart(AddCartItemRequestDto request);


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
     * @param request UpdateCartItemRequestDto object
     */
    void updateItemQuantity(UpdateCartItemRequestDto request);


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
    List<CartItem> getCartItemsByCartId(Long cartId);
}
