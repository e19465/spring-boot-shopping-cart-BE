package com.sasindu.shoppingcart.controllers;


import com.sasindu.shoppingcart.abstractions.dto.request.cartitem.UpdateCartItemRequestDto;
import com.sasindu.shoppingcart.abstractions.dto.response.cartitem.CartItemResponseDto;
import com.sasindu.shoppingcart.abstractions.interfaces.ICartItemService;
import com.sasindu.shoppingcart.abstractions.interfaces.ICartService;
import com.sasindu.shoppingcart.helpers.ApiResponse;
import com.sasindu.shoppingcart.helpers.GlobalExceptionHandler;
import com.sasindu.shoppingcart.helpers.GlobalSuccessHandler;
import com.sasindu.shoppingcart.helpers.ValidationHelper;
import com.sasindu.shoppingcart.models.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}" + "/cart-item")
public class CartItemController {
    private final ICartItemService _cartItemService;
    private final ICartService _cartService;


    /**
     * Add an item to the cart
     * <p>
     * //     * @param request The request object
     *
     * @return The response entity
     */
    // After Auth Context is implemented, the request body of type AddCartItemRequest will be used
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart(
            @RequestParam Long userId,
            @RequestParam Long productId,
            @RequestParam int quantity
    ) {
        try {
            _cartItemService.addItemToCart(userId, productId, quantity);
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
    public ResponseEntity<ApiResponse> updateItemQuantity(@RequestBody UpdateCartItemRequestDto request) {
        try {
            // Validate the request
            ValidationHelper.validateModelBinding(request);

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
     * Get cart items by user id
     *
     * @param userId The id of the user
     * @return The response entity
     */
    @GetMapping("/get-by-user-id/{userId}")
    public ResponseEntity<ApiResponse> getCartItemsByUserId(@PathVariable Long userId) {
        try {
            List<CartItemResponseDto> cartItems = _cartItemService.getCartItemsByUserId(userId)
                    .stream().map(CartItem::toCartItemResponse).toList();
            return GlobalSuccessHandler.handleSuccess("Cart items retrieved successfully", cartItems, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e);
        }
    }


    /**
     * Get cart items by cart id
     *
     * @param cartId The id of the cart
     * @return The response entity
     */
    @GetMapping("/get-by-cart-id/{cartId}/{userId}")
    public ResponseEntity<ApiResponse> getCartItemsByCartId(@PathVariable Long cartId, @PathVariable Long userId) {
        try {
            List<CartItemResponseDto> cartItems = _cartItemService.getCartItemsByCartId(cartId, userId)
                    .stream().map(CartItem::toCartItemResponse).toList();
            return GlobalSuccessHandler.handleSuccess("Cart items retrieved successfully", cartItems, HttpStatus.OK.value(), null);
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
 * 4. get cart items by user id - GET - http://localhost:9091/api/v1/cart-item/get-by-user-id/{userId}
 * 5. get cart items by cart id - GET - http://localhost:9091/api/v1/cart-item/get-by-cart-id/{cartId}/{userId}
 */