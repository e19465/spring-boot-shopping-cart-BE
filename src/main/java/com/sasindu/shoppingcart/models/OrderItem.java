package com.sasindu.shoppingcart.models;

import com.sasindu.shoppingcart.abstractions.dto.response.orderitem.OrderItemResponseDto;
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
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    private BigDecimal price = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public OrderItem(
            Order order,
            Product product,
            int quantity,
            BigDecimal price
    ) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }


    /**
     * Convert OrderItem entity to OrderItemResponse object
     *
     * @return the response object
     */
    public OrderItemResponseDto toOrderItemResponse() {
        OrderItemResponseDto response = new OrderItemResponseDto();
        response.setId(this.id);
        response.setQuantity(this.quantity);
        response.setPrice(this.price);
        response.setOrder(this.order.toOrderResponse());
        response.setProduct(this.product.toProductResponse());
        return response;
    }
}
