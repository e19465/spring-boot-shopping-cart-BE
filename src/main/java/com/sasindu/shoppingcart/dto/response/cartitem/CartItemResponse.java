package com.sasindu.shoppingcart.dto.response.cartitem;


import com.sasindu.shoppingcart.dto.response.product.ProductResponse;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemResponse {
    private Long id;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private ProductResponse product;
}
