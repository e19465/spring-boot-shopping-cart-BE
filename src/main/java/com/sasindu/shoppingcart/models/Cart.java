package com.sasindu.shoppingcart.models;


import com.sasindu.shoppingcart.dto.response.cart.CartResponse;
import com.sasindu.shoppingcart.dto.response.cartitem.CartItemResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal totalAmount = BigDecimal.ZERO;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> cartItems;

    /**
     * Add a cart item to the cart
     *
     * @param cartItem the cart item to add
     */
    public void addCartItem(CartItem cartItem) {
        this.cartItems.add(cartItem);
        cartItem.setCart(this);
        this.updateTotalAmount();
    }


    /**
     * Remove a cart item from the cart
     *
     * @param cartItem the cart item to remove
     */
    public void removeCartItem(CartItem cartItem) {
        this.cartItems.remove(cartItem);
        cartItem.setCart(null);
        this.updateTotalAmount();
    }


    /**
     * Update the total amount of the cart
     */
    public void updateTotalAmount() {
        this.totalAmount = this.cartItems.stream()
                .map(item -> {
                    BigDecimal unitPrice = item.getUnitPrice();
                    if (unitPrice == null) {
                        unitPrice = BigDecimal.ZERO;
                    }
                    return unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    /**
     * Convert the cart to a cart response
     *
     * @return the cart response
     */
    public CartResponse toCartResponse() {
        CartResponse response = new CartResponse();
        response.setId(this.id);
        response.setTotalAmount(this.totalAmount);

        // Map CartItem to CartItemResponse and set it to the CartResponse
        Set<CartItemResponse> cartItemResponses = this.cartItems.stream()
                .map(CartItem::toCartItemResponse)  // Convert CartItem to CartItemResponse
                .collect(Collectors.toSet());
        response.setCartItems(cartItemResponses);
        return response;
    }
}
