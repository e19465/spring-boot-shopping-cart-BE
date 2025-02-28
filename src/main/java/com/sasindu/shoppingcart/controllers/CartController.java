package com.sasindu.shoppingcart.controllers;

import com.sasindu.shoppingcart.abstractions.dto.response.cart.CartResponseDto;
import com.sasindu.shoppingcart.abstractions.interfaces.ICartService;
import com.sasindu.shoppingcart.helpers.ApiResponse;
import com.sasindu.shoppingcart.helpers.ErrorResponseHandler;
import com.sasindu.shoppingcart.helpers.SuccessResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}" + "/cart")
public class CartController {
    private final ICartService _cartService;


    /**
     * getCartById method is responsible for fetching a cart by its id
     * this method calls the getCartById method of the CartService class internally
     *
     * @param cartId Long value of the cart id
     * @return ApiResponse object containing the response details
     */
    @GetMapping("/find-by-id/{cartId}")
    public ResponseEntity<ApiResponse> getCartById(@PathVariable Long cartId) {
        try {
            CartResponseDto cart = _cartService.getCartById(cartId).toCartResponse();
            return SuccessResponseHandler.handleSuccess("Cart fetched successfully", cart, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return ErrorResponseHandler.handleException(e);
        }
    }


    /**
     * clearCart method is responsible for clearing a cart by its id
     * this method calls the clearCart method of the CartService class internally
     *
     * @param id Long value of the cart id
     * @return ApiResponse object containing the response details
     */
    @DeleteMapping("/clear/{id}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long id) {
        try {
            _cartService.clearCart(id);
            return SuccessResponseHandler.handleSuccess("Cart cleared successfully", null, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return ErrorResponseHandler.handleException(e);
        }
    }


    /**
     * getTotalPrice method is responsible for fetching the total price of a cart by its id
     * this method calls the getTotalPrice method of the CartService class internally
     *
     * @param id Long value of the cart id
     * @return ApiResponse object containing the response details
     */
    @GetMapping("/total-price/{id}")
    public ResponseEntity<ApiResponse> getTotalPrice(@PathVariable Long id) {
        try {
            BigDecimal totalPrice = _cartService.getTotalPrice(id);
            return SuccessResponseHandler.handleSuccess("Total price fetched successfully", totalPrice, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return ErrorResponseHandler.handleException(e);
        }
    }
}

/*
 * ENDPOINTS
 * 1. get by id - GET - http://localhost:9091/api/v1/cart/find-by-id/{id}
 * 2. clear cart - DELETE - http://localhost:9091/api/v1/cart/clear/{id}
 * 3. get total price - GET - http://localhost:9091/api/v1/cart/total-price/{id}
 */