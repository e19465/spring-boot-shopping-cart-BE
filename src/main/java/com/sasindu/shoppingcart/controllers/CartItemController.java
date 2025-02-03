package com.sasindu.shoppingcart.controllers;


import com.sasindu.shoppingcart.abstractions.ICartItemService;
import com.sasindu.shoppingcart.abstractions.ICartService;
import com.sasindu.shoppingcart.constants.ApplicationConstants;
import com.sasindu.shoppingcart.dto.request.cartitem.AddCartItemRequest;
import com.sasindu.shoppingcart.dto.request.cartitem.UpdateCartItemRequest;
import com.sasindu.shoppingcart.helpers.ApiResponse;
import com.sasindu.shoppingcart.helpers.GlobalExceptionHandler;
import com.sasindu.shoppingcart.helpers.GlobalSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApplicationConstants.API_URL_PREFIX + "/cart-item")
public class CartItemController {
    private final ICartItemService _cartItemService;
    private final ICartService _cartService;


    /**
     * Add an item to the cart
     *
     * @param request The request object
     * @return The response entity
     */
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddCartItemRequest request) {
        try {
            Long cartId = request.getCartId();

            if (cartId == null) {
                cartId = _cartService.initializeNewCart();
            }

            Long productId = request.getProductId();
            int quantity = request.getQuantity();
            _cartItemService.addItemToCart(cartId, productId, quantity);
            return GlobalSuccessHandler.handleSuccess("Item added to the cart successfully", null, HttpStatus.CREATED.value(), null);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e);
        }
    }


    /**
     * Remove an item from the cart
     *
     * @param cartId    The id of the cart
     * @param productId The id of the product
     * @return The response entity
     */
    @DeleteMapping("/remove/{cartId}/{productId}")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
        try {
            _cartItemService.removeItemFromCart(cartId, productId);
            return GlobalSuccessHandler.handleSuccess("Item removed from the cart successfully", null, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e);
        }
    }


    /**
     * Update the quantity of an item in the cart
     *
     * @param request The request object
     * @return The response entity
     */
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(@RequestBody UpdateCartItemRequest request) {
        try {
            Long cartId = request.getCartId();
            Long productId = request.getProductId();
            int quantity = request.getQuantity();
            _cartItemService.updateItemQuantity(cartId, productId, quantity);
            return GlobalSuccessHandler.handleSuccess("Item quantity updated successfully", null, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e);
        }
    }


    /**
     * Clear all items in the cart
     *
     * @param cartId The id of the cart
     * @return The response entity
     */
    @DeleteMapping("/clear-by-cart-id/{cartId}")
    public ResponseEntity<ApiResponse> clearCartItemsByCartId(@PathVariable Long cartId) {
        try {
            _cartItemService.deleteAllByCartId(cartId);
            return GlobalSuccessHandler.handleSuccess("Cart items cleared successfully", null, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e);
        }
    }
}


/*
 * ENDPOINTS
 * 1. add item - POST - http://localhost:9091/api/v1/cart-item/add
 * 2. remove item - DELETE - http://localhost:9091/api/v1/cart-item/remove/{cartId}/{productId}
 * 3. update item - PUT - http://localhost:9091/api/v1/cart-item/update
 * 4. clear cart items by cart id - DELETE - http://localhost:9091/api/v1/cart-item/clear-by-cart-id/{cartId}
 */