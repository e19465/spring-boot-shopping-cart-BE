package com.sasindu.shoppingcart.dto.response.cartitem;


import com.sasindu.shoppingcart.models.Product;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemResponse {
    private Long id;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private Product product;
    private Long cartId;
}
