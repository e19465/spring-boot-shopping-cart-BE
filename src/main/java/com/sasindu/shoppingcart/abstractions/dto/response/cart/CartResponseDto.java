package com.sasindu.shoppingcart.abstractions.dto.response.cart;

import com.sasindu.shoppingcart.abstractions.dto.response.cartitem.CartItemResponseDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class CartResponseDto {
    private Long id;
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private Set<CartItemResponseDto> cartItems;
}
