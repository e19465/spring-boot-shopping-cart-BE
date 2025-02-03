package com.sasindu.shoppingcart.dto.request.cartitem;

import lombok.Data;

@Data
public class UpdateCartItemRequest {
    private Long cartId;
    private Long productId;
    private int quantity;
}
