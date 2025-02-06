package com.sasindu.shoppingcart.models;


import com.sasindu.shoppingcart.abstractions.dto.response.cartitem.CartItemResponseDto;
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
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    private BigDecimal unitPrice = BigDecimal.ZERO;

    private BigDecimal totalPrice = BigDecimal.ZERO;

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
    public CartItemResponseDto toCartItemResponse() {
        CartItemResponseDto response = new CartItemResponseDto();
        response.setId(this.id);
        response.setQuantity(this.quantity);
        response.setUnitPrice(this.unitPrice);
        response.setTotalPrice(this.totalPrice);
        response.setCartId(this.cart.getId());
        response.setProduct(this.product.toProductResponse());
        return response;
    }
}
