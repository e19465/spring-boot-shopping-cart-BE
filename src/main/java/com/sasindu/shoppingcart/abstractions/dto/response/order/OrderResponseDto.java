package com.sasindu.shoppingcart.abstractions.dto.response.order;


import com.sasindu.shoppingcart.abstractions.dto.response.orderitem.OrderItemResponseDto;
import com.sasindu.shoppingcart.abstractions.dto.response.user.UserResponseDto;
import com.sasindu.shoppingcart.abstractions.enums.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderResponseDto {
    private Long id;
    private LocalDate orderDate;
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private UserResponseDto user;
    private List<OrderItemResponseDto> orderItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}

