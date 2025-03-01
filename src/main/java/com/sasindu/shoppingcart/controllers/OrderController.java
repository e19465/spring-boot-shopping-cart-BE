package com.sasindu.shoppingcart.controllers;


import com.sasindu.shoppingcart.abstractions.dto.response.order.OrderResponseDto;
import com.sasindu.shoppingcart.abstractions.interfaces.IOrderService;
import com.sasindu.shoppingcart.helpers.ApiResponse;
import com.sasindu.shoppingcart.helpers.ErrorResponseHandler;
import com.sasindu.shoppingcart.helpers.SuccessResponseHandler;
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
     * @return ApiResponse object containing the response details
     */
    @PostMapping("/place-order")
    public ResponseEntity<ApiResponse> placeOrder() {
        try {
            OrderResponseDto response = _orderService.placeOrder().toOrderResponse();
            return SuccessResponseHandler.handleSuccess("Order placed successfully", response, HttpStatus.CREATED.value(), null);
        } catch (Exception e) {
            return ErrorResponseHandler.handleException(e);
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
            return SuccessResponseHandler.handleSuccess("Order retrieved successfully", response, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return ErrorResponseHandler.handleException(e);
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
            return SuccessResponseHandler.handleSuccess("Orders retrieved successfully", response, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return ErrorResponseHandler.handleException(e);
        }
    }

    @DeleteMapping("/cancel-order/{orderId}")
    public ResponseEntity<ApiResponse> cancelOrder(@PathVariable Long orderId) {
        try {
            _orderService.cancelOrder(orderId);
            return SuccessResponseHandler.handleSuccess("Order cancelled successfully", null, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return ErrorResponseHandler.handleException(e);
        }
    }
}


/*
 * ENDPOINTS
 * 1. place order - POST - http://localhost:9091/api/v1/orders/place-order
 * 2. get order by id - GET - http://localhost:9091/api/v1/orders/find-by-id/{orderId}
 * 3. get orders by user id - GET - http://localhost:9091/api/v1/orders/get-by-user-id/{userId}
 * 4. cancel order - DELETE - http://localhost:9091/api/v1/orders/cancel-order/{orderId}
 */