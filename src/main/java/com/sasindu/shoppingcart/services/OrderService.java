package com.sasindu.shoppingcart.services;


import com.sasindu.shoppingcart.abstractions.enums.OrderStatus;
import com.sasindu.shoppingcart.abstractions.interfaces.*;
import com.sasindu.shoppingcart.exceptions.BadRequestException;
import com.sasindu.shoppingcart.exceptions.NotFoundException;
import com.sasindu.shoppingcart.exceptions.UnAuthorizedException;
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
            order.setAppUser(cart.getAppUser());
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

            if (!isAdmin && !order.getAppUser().getId().equals(appUser.getId())) {
                throw new UnAuthorizedException("Unauthorized access");
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
                throw new UnAuthorizedException("Unauthorized access");
            }
            return _orderRepository.findAllByUserId(userId);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
