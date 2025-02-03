package com.sasindu.shoppingcart.services;

import com.sasindu.shoppingcart.abstractions.ICartItemService;
import com.sasindu.shoppingcart.abstractions.ICartService;
import com.sasindu.shoppingcart.exceptions.NotFoundException;
import com.sasindu.shoppingcart.models.Cart;
import com.sasindu.shoppingcart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;


@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository _cartRepository;
    private final ICartItemService _cartItemService;
    private final AtomicLong cartIdGenerator = new AtomicLong(0);

    /**
     * Get the cart by id and calculate the total amount and set the total amount to the cart and return the cart
     *
     * @param id the id of the cart
     * @return the cart
     */
    @Override
    public Cart getCartById(Long id) {
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
            Cart cart = getCartById(id);
            _cartItemService.deleteAllByCartId(id);
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
            Cart cart = getCartById(id);
            return cart.getTotalAmount();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Save the cart
     *
     * @param cart the cart to save
     * @return the saved cart
     */
    @Override
    public Cart saveCart(Cart cart) {
        try {
            return _cartRepository.save(cart);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Initialize a new cart - for internal use until Auth Context is implemented
     *
     * @return the id of the new cart
     */
    @Override
    public Long initializeNewCart() {
        try {
            Cart cart = new Cart();
            cart.setId(cartIdGenerator.incrementAndGet());
            cart.setTotalAmount(BigDecimal.ZERO);
            _cartRepository.save(cart);
            return cart.getId();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
