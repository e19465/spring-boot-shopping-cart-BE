package com.sasindu.shoppingcart.controllers;

import com.sasindu.shoppingcart.abstractions.ICartService;
import com.sasindu.shoppingcart.constants.ApplicationConstants;
import com.sasindu.shoppingcart.dto.response.cart.CartResponse;
import com.sasindu.shoppingcart.helpers.ApiResponse;
import com.sasindu.shoppingcart.helpers.GlobalExceptionHandler;
import com.sasindu.shoppingcart.helpers.GlobalSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApplicationConstants.API_URL_PREFIX + "/cart")
public class CartController {
    private final ICartService _cartService;


    /**
     * getCartById method is responsible for fetching a cart by its id
     * this method calls the getCartById method of the CartService class internally
     *
     * @param id Long value of the cart id
     * @return ApiResponse object containing the response details
     */
    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<ApiResponse> getCartById(@PathVariable Long id) {
        try {
            CartResponse cart = _cartService.getCartById(id).toCartResponse();
            return GlobalSuccessHandler.handleSuccess("Cart fetched successfully", cart, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e);
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
            return GlobalSuccessHandler.handleSuccess("Cart cleared successfully", null, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e);
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
            return GlobalSuccessHandler.handleSuccess("Total price fetched successfully", totalPrice, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e);
        }
    }
}

/*
 * ENDPOINTS
 * 1. get by id - GET - http://localhost:9091/api/v1/cart/find-by-id/{id}
 * 2. clear cart - DELETE - http://localhost:9091/api/v1/cart/clear/{id}
 * 3. get total price - GET - http://localhost:9091/api/v1/cart/total-price/{id}
 */