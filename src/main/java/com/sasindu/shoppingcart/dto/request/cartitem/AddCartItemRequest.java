package com.sasindu.shoppingcart.dto.request.cartitem;


import lombok.Data;

@Data
public class AddCartItemRequest {
    private Object cartId;
    private Long productId;
    private int quantity;
}
