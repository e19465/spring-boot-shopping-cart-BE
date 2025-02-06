package com.sasindu.shoppingcart.services;


import com.sasindu.shoppingcart.abstractions.enums.OrderStatus;
import com.sasindu.shoppingcart.abstractions.interfaces.IOrderService;
import com.sasindu.shoppingcart.abstractions.interfaces.ISharedService;
import com.sasindu.shoppingcart.exceptions.BadRequestException;
import com.sasindu.shoppingcart.exceptions.NotFoundException;
import com.sasindu.shoppingcart.models.*;
import com.sasindu.shoppingcart.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


/**
 * OrderService class is responsible for handling all the business logic related to the order
 * It implements the IOrderService interface
 */
@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository _orderRepository;
    private final ISharedService _sharedService;

    /**
     * Create the order items for order.This is used as internal helper method to create an order
     *
     * @param order the order
     * @param cart  the cart
     * @return the list of order items
     */
    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        try {
            return cart.getCartItems().stream()
                    .map(cartItem -> {
                        Product product = cartItem.getProduct();
                        product.setInventory(product.getInventory() - cartItem.getQuantity());
                        _sharedService.saveProduct(product);
                        return new OrderItem(order, product, cartItem.getQuantity(), product.getPrice());
                    }).collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Create order. This is used as internal helper method to create an order
     *
     * @param cart the cart
     * @return the order
     */
    private Order createOrder(Cart cart) {
        try {
            Order order = new Order();
            order.setUser(cart.getUser());
            order.setStatus(OrderStatus.PENDING);
            order.setOrderDate(LocalDate.now());
            return order;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Place order.
     *
     * @param userId the user id
     * @return the order
     */
    @Override
    @Transactional
    public Order placeOrder(Long userId) {
        try {
            User user = _sharedService.getUserById(userId);
            if (user == null) {
                throw new NotFoundException("User not found");
            }

            Cart cart = _sharedService.findCartByUserId(userId);
            if (cart == null) {
                throw new NotFoundException("Cart not found");
            }

            // create the order
            Order order = createOrder(cart);
            List<OrderItem> orderItems = createOrderItems(order, cart);

            if (orderItems.isEmpty()) {
                throw new BadRequestException("Order items are empty");
            }

            order.addOrderItems(orderItems);
            Order savedOrder = _orderRepository.save(order);

            // clear the cart
            _sharedService.clearCartByCart(cart);

            return savedOrder;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Gets order.
     *
     * @param orderId the order id
     * @return the order
     */
    @Override
    public Order getOrderById(Long orderId) {
        try {
            return _orderRepository
                    .findById(orderId)
                    .orElseThrow(() -> new NotFoundException("Order not found"));
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Gets orders by user id.
     *
     * @param userId the user id
     * @return the orders by user id
     */
    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        try {
            return _orderRepository.findAllByUserId(userId);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
