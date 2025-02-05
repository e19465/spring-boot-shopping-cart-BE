package com.sasindu.shoppingcart.abstractions.dto.response.cart;

import com.sasindu.shoppingcart.abstractions.dto.response.cartitem.CartItemResponse;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class CartResponse {
    private Long id;
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private Set<CartItemResponse> cartItems;
}
