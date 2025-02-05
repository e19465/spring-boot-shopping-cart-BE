package com.sasindu.shoppingcart.models;


import com.sasindu.shoppingcart.abstractions.dto.response.cartitem.CartItemResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    private BigDecimal unitPrice;

    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;


    /**
     * Set the total price of the cart item
     */
    public void setTotalPrice() {
        this.totalPrice = this.unitPrice.multiply(BigDecimal.valueOf(this.quantity));
    }


    /**
     * Convert the cart item to a cart item response
     *
     * @return CartItemResponse
     */
    public CartItemResponse toCartItemResponse() {
        CartItemResponse response = new CartItemResponse();
        response.setId(this.id);
        response.setQuantity(this.quantity);
        response.setUnitPrice(this.unitPrice);
        response.setTotalPrice(this.totalPrice);
        response.setProduct(this.product.toProductResponse());
        return response;
    }
}
