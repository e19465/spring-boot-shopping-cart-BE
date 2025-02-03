package com.sasindu.shoppingcart.dto.request.cartitem;


import lombok.Data;

@Data
public class AddCartItemRequest {
    private Long cartId;
    private Long productId;
    private int quantity;
}
