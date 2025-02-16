package com.sasindu.shoppingcart.controllers;


import com.sasindu.shoppingcart.abstractions.dto.response.order.OrderResponseDto;
import com.sasindu.shoppingcart.abstractions.interfaces.IOrderService;
import com.sasindu.shoppingcart.helpers.ApiResponse;
import com.sasindu.shoppingcart.helpers.GlobalExceptionHandler;
import com.sasindu.shoppingcart.helpers.GlobalSuccessHandler;
import com.sasindu.shoppingcart.models.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * OrderController class is responsible for handling all the API requests related to the order
 */
@RestController
@RequestMapping("${api.prefix}" + "/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService _orderService;


    /**
     * Place order method is responsible for placing an order
     * this method calls the placeOrder method of the OrderService class internally
     *
     * @param userId Long value of the user id
     * @return ApiResponse object containing the response details
     */
    @PostMapping("/place-order/{userId}")
    public ResponseEntity<ApiResponse> placeOrder(@PathVariable Long userId) {
        try {
            OrderResponseDto response = _orderService.placeOrder(userId).toOrderResponse();
            return GlobalSuccessHandler.handleSuccess("Order placed successfully", response, HttpStatus.CREATED.value(), null);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e);
        }
    }


    /**
     * Get order by id method is responsible for getting an order by id
     * this method calls the getOrderById method of the OrderService class internally
     *
     * @param orderId Long value of the order id
     * @return ApiResponse object containing the response details
     */
    @GetMapping("/find-by-id/{orderId}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId) {
        try {
            OrderResponseDto response = _orderService.getOrderById(orderId).toOrderResponse();
            return GlobalSuccessHandler.handleSuccess("Order retrieved successfully", response, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e);
        }
    }


    /**
     * Get orders by user id method is responsible for getting orders by user id
     * this method calls the getOrdersByUserId method of the OrderService class internally
     *
     * @param userId Long value of the user id
     * @return ApiResponse object containing the response details
     */
    @GetMapping("/get-by-user-id/{userId}")
    public ResponseEntity<ApiResponse> getOrdersByUserId(@PathVariable Long userId) {
        try {
            List<OrderResponseDto> response = _orderService.getOrdersByUserId(userId)
                    .stream()
                    .map(Order::toOrderResponse)
                    .toList();
            return GlobalSuccessHandler.handleSuccess("Orders retrieved successfully", response, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e);
        }
    }
}


/*
 * ENDPOINTS
 * 1. place order - POST - http://localhost:9091/api/v1/orders/place-order/{userId}
 * 2. get order by id - GET - http://localhost:9091/api/v1/orders/find-by-id/{orderId}
 * 3. get orders by user id - GET - http://localhost:9091/api/v1/orders/get-by-user-id/{userId}
 */