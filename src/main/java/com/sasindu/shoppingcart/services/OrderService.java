package com.sasindu.shoppingcart.services;


import com.sasindu.shoppingcart.abstractions.enums.OrderStatus;
import com.sasindu.shoppingcart.abstractions.interfaces.*;
import com.sasindu.shoppingcart.constants.ApplicationConstants;
import com.sasindu.shoppingcart.exceptions.BadRequestException;
import com.sasindu.shoppingcart.exceptions.ForbiddenException;
import com.sasindu.shoppingcart.exceptions.NotFoundException;
import com.sasindu.shoppingcart.models.*;
import com.sasindu.shoppingcart.repository.OrderItemRepository;
import com.sasindu.shoppingcart.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * OrderService class is responsible for handling all the business logic related to the order
 * It implements the IOrderService interface
 */
@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository _orderRepository;
    private final OrderItemRepository _orderItemRepository;
    private final IProductService _productService;
    private final IUserService _userService;
    private final IAuthService _authService;
    private final ICartService _cartService;

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
                        int newInventory = product.getInventory() - cartItem.getQuantity();
                        if (newInventory < 0) {
                            throw new BadRequestException("Inventory is not enough for product: " + product.getName());
                        }
                        product.setInventory(newInventory);
                        _productService.saveProduct(product);
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
     * @return the order
     */
    @Override
    @Transactional
    public Order placeOrder() {
        try {
            AppUser appUser = _authService.getAuthenticatedUser();
            Cart cart = _cartService.getCartByUserId(appUser.getId());

            if (!Objects.equals(cart.getUser().getId(), appUser.getId())) {
                throw new ForbiddenException("Access denied");
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
            _cartService.clearCartByCart(cart);

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
            AppUser appUser = _authService.getAuthenticatedUser();
            boolean isAdmin = _authService.isAuthenticatedUserAdmin();
            Order order = _orderRepository
                    .findById(orderId)
                    .orElseThrow(() -> new NotFoundException("Order not found"));

            if (!isAdmin && !order.getUser().getId().equals(appUser.getId())) {
                throw new ForbiddenException("Access denied");
            }
            return order;
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
            if (!_authService.isAuthenticatedUserAdmin() && !_authService.checkLoggedInUserWithId(userId)) {
                throw new ForbiddenException("Access denied");
            }
            if (_userService.getUserById(userId) == null) {
                throw new NotFoundException("User not found");
            }
            return _orderRepository.findAllByUserId(userId);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Cancel order. Cancels the order and updates the product inventory
     * if the order is not cancelled and MAXIMUM_ORDER_CANCEL_DAYS is not exceeded
     *
     * @param orderId the order id
     */
    @Override
    @Transactional
    public void cancelOrder(Long orderId) {
        try {
            AppUser authenticatedUser = _authService.getAuthenticatedUser();
            Order order = getOrderById(orderId);

            if (!order.getUser().getId().equals(authenticatedUser.getId())) {
                throw new ForbiddenException("Access denied");
            }

            if (order.getStatus() == OrderStatus.CANCELLED) {
                throw new BadRequestException("Order already cancelled");
            }

            // Get the current date
            LocalDate currentDate = LocalDate.now();
            // Calculate the difference in days
            long daysSinceOrder = ChronoUnit.DAYS.between(order.getOrderDate(), currentDate);

            // check order can be cancelled
            if (daysSinceOrder > ApplicationConstants.MAXIMUM_ORDER_CANCEL_DAYS) {
                throw new BadRequestException("Order cannot be cancelled after " + ApplicationConstants.MAXIMUM_ORDER_CANCEL_DAYS + " days. Please contact support for further assistance.");
            }

            // get order quantity and set product inventory
            for (OrderItem orderItem : order.getOrderItems()) {
                Product product = orderItem.getProduct();
                product.setInventory(product.getInventory() + orderItem.getQuantity());
                _productService.saveProduct(product);
            }

            // Update order status before deleting order items
            order.setStatus(OrderStatus.CANCELLED);
            _orderRepository.save(order);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
