package com.sasindu.shoppingcart.dto.response.cart;

import com.sasindu.shoppingcart.models.CartItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class CartResponse {
    private Long id;
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private Set<CartItem> cartItems;
}
