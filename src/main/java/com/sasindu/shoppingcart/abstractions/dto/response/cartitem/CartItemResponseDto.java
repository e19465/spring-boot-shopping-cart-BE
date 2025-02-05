package com.sasindu.shoppingcart.abstractions.dto.response.cartitem;


import com.sasindu.shoppingcart.abstractions.dto.response.product.ProductResponseDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemResponseDto {
    private Long id;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private ProductResponseDto product;
}
