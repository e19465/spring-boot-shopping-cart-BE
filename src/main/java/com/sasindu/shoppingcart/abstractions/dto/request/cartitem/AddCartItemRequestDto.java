package com.sasindu.shoppingcart.abstractions.dto.request.cartitem;


import lombok.Data;

@Data
public class AddCartItemRequestDto {
    private Object cartId;
    private Long productId;
    private int quantity;
}
