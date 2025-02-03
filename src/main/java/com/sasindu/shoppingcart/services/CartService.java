package com.sasindu.shoppingcart.services;

import com.sasindu.shoppingcart.abstractions.ICartService;
import com.sasindu.shoppingcart.exceptions.NotFoundException;
import com.sasindu.shoppingcart.models.Cart;
import com.sasindu.shoppingcart.repository.CartItemRepository;
import com.sasindu.shoppingcart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository _cartRepository;
    private final CartItemRepository _cartItemRepository;


    /**
     * Get the cart by id and calculate the total amount and set the total amount to the cart and return the cart
     *
     * @param id the id of the cart
     * @return the cart
     */
    @Override
    public Cart getCart(Long id) {
        try {
            return _cartRepository.findById(id)
                    .map(c -> {
                        c.setTotalAmount(c.getTotalAmount());
                        return _cartRepository.save(c);
                    })
                    .orElseThrow(() -> new NotFoundException("Cart not found"));
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Clear the cart by id (delete all cart items and delete the cart)
     *
     * @param id the id of the cart
     */
    @Override
    public void clearCart(Long id) {
        try {
            Cart cart = getCart(id);
            _cartItemRepository.deleteAllByCartId(id);
            cart.getCartItems().clear();
            _cartRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Get the total price of the cart by id
     *
     * @param id the id of the cart
     * @return the total price of the cart
     */
    @Override
    public BigDecimal getTotalPrice(Long id) {
        try {
            Cart cart = getCart(id);
            return cart.getTotalAmount();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
