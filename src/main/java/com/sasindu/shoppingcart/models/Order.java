package com.sasindu.shoppingcart.models;


import com.sasindu.shoppingcart.abstractions.dto.response.order.OrderResponseDto;
import com.sasindu.shoppingcart.abstractions.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate orderDate;

    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> orderItems = new HashSet<>();


    /**
     * Add an order item to the order
     *
     * @param orderItem the order item to add
     */
    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
        this.updateTotalAmount();
    }


    /**
     * Add a list of order items to the order
     *
     * @param orderItems the list of order items to add
     */
    public void addOrderItems(List<OrderItem> orderItems) {
        orderItems.forEach(this::addOrderItem);
    }

    /**
     * Remove an order item from the order
     *
     * @param orderItem the order item to remove
     */
    public void removeOrderItem(OrderItem orderItem) {
        this.orderItems.remove(orderItem);
        orderItem.setOrder(null);
        this.updateTotalAmount();
    }

    /**
     * Update the total amount of the order
     */
    public void updateTotalAmount() {
        this.totalAmount = this.orderItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    /**
     * Convert the order to an order response
     *
     * @return the order response
     */
    public OrderResponseDto toOrderResponse() {
        OrderResponseDto response = new OrderResponseDto();
        response.setId(this.id);
        response.setOrderDate(this.orderDate);
        response.setTotalAmount(this.totalAmount);
        response.setStatus(this.status);
        response.setUser(this.user.toUserResponse());
        response.setOrderItems(this.orderItems.stream().map(OrderItem::toOrderItemResponse).toList());
        return response;
    }

}
