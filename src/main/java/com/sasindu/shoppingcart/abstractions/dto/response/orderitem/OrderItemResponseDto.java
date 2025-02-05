package com.sasindu.shoppingcart.abstractions.dto.response.orderitem;

import com.sasindu.shoppingcart.abstractions.dto.response.order.OrderResponseDto;
import com.sasindu.shoppingcart.abstractions.dto.response.product.ProductResponseDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemResponseDto {
    private Long id;
    private int quantity;
    private BigDecimal price = BigDecimal.ZERO;
    private OrderResponseDto order;
    private ProductResponseDto product;
}
