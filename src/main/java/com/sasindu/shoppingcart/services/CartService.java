package com.sasindu.shoppingcart.services;

import com.sasindu.shoppingcart.abstractions.interfaces.IAuthService;
import com.sasindu.shoppingcart.abstractions.interfaces.ICartService;
import com.sasindu.shoppingcart.exceptions.ForbiddenException;
import com.sasindu.shoppingcart.exceptions.NotFoundException;
import com.sasindu.shoppingcart.models.AppUser;
import com.sasindu.shoppingcart.models.Cart;
import com.sasindu.shoppingcart.repository.CartItemRepository;
import com.sasindu.shoppingcart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


/**
 * CartService class is responsible for handling the business logic related to the cart
 */
@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository _cartRepository;
    private final CartItemRepository _cartItemRepository;
    private final IAuthService _authService;

    /**
     * Get the cart by id and calculate the total amount and set the total amount to the cart and return the cart
     * Accessible only by the authenticated cart owner or admin
     *
     * @param cartId the id of the cart
     * @return the cart
     */
    @Override
    public Cart getCartById(Long cartId) {
        try {
            boolean isUserAdmin = _authService.isAuthenticatedUserAdmin();
            AppUser authenticatedUser = _authService.getAuthenticatedUser();
            Cart cart = _cartRepository.findById(cartId)
                    .orElseThrow(() -> new NotFoundException("Cart not found"));
            if (!isUserAdmin && !cart.getUser().getId().equals(authenticatedUser.getId())) {
                throw new ForbiddenException("Access denied");
            }
            return cart;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Clear the cart by id (delete all cart items and delete the cart)
     * Accessible only by the authenticated cart owner
     *
     * @param id the id of the cart
     */
    @Override
    @Transactional
    public void clearCart(Long id) {
        try {
            Cart cart = getCartById(id);
            clearCartByCart(cart);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Get the total price of the cart by id
     * Accessible only by the authenticated cart owner or admin
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
     * Get a cart by user id - for internal use in cart item service
     *
     * @param userId The id of the user
     * @return The cart if found, null if not found returns null
     */
    @Override
    public Cart getCartByUserId(Long userId) {
        try {
            boolean isUserAdmin = _authService.isAuthenticatedUserAdmin();
            AppUser authenticatedUser = _authService.getAuthenticatedUser();
            Cart cart = _cartRepository.findByUserId(userId);

            if (cart == null) {
                throw new NotFoundException("Cart not found");
            }

            if (!isUserAdmin && !cart.getUser().getId().equals(authenticatedUser.getId())) {
                throw new ForbiddenException("Access denied");
            }

            return cart;
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
    public Cart initializeNewCart(AppUser appUser) {
        try {
            Cart cart = new Cart();
            cart.setTotalAmount(BigDecimal.ZERO);
            cart.setUser(appUser);
            return _cartRepository.save(cart);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Clear cart - remove all cart items and set total amount to 0
     *
     * @param cart The cart object
     */
    @Override
    public void clearCartByCart(Cart cart) {
        try {
            AppUser authenticatedUser = _authService.getAuthenticatedUser();
            if (!cart.getUser().getId().equals(authenticatedUser.getId())) {
                throw new ForbiddenException("Access denied");
            }
            _cartItemRepository.deleteAllByCartId(cart.getId());
            cart.getCartItems().clear();
            cart.updateTotalAmount();
            _cartRepository.save(cart);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
