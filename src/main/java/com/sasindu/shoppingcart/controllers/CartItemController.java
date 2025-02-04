package com.sasindu.shoppingcart.controllers;


import com.sasindu.shoppingcart.abstractions.ICartItemService;
import com.sasindu.shoppingcart.abstractions.ICartService;
import com.sasindu.shoppingcart.constants.ApplicationConstants;
import com.sasindu.shoppingcart.dto.request.cartitem.UpdateCartItemRequest;
import com.sasindu.shoppingcart.helpers.ApiResponse;
import com.sasindu.shoppingcart.helpers.GlobalExceptionHandler;
import com.sasindu.shoppingcart.helpers.GlobalSuccessHandler;
import com.sasindu.shoppingcart.helpers.ValidationHelper;
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
     * <p>
     * //     * @param request The request object
     *
     * @return The response entity
     */
    // After Auth Context is implemented, the request body of type AddCartItemRequest will be used
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart(
            @RequestParam(required = false) Long cartId,
            @RequestParam Long productId,
            @RequestParam int quantity
    ) {
        try {
            // Validate the request
//            ValidationHelper.validateModelBinding(request);


            if (cartId == null) {
                cartId = _cartService.initializeNewCart();
            }

//            Long cartId = request.getCartId();
//            Long productId = request.getProductId();
//            int quantity = request.getQuantity();


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
}


/*
 * ENDPOINTS
 * 1. add item - POST - http://localhost:9091/api/v1/cart-item/add
 * 2. remove item - DELETE - http://localhost:9091/api/v1/cart-item/remove/{cartId}/{productId}
 * 3. update item - PUT - http://localhost:9091/api/v1/cart-item/update
 */