package com.sasindu.shoppingcart.services;

import com.sasindu.shoppingcart.abstractions.interfaces.ISharedService;
import com.sasindu.shoppingcart.models.AppUser;
import com.sasindu.shoppingcart.models.Cart;
import com.sasindu.shoppingcart.models.Category;
import com.sasindu.shoppingcart.models.Product;
import com.sasindu.shoppingcart.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


/**
 * Shared service - This service contains shared methods that can be used in any service
 */
@Service
@RequiredArgsConstructor
public class SharedService implements ISharedService {
    private final CategoryRepository _categoryRepository;
    private final CartRepository _cartRepository;
    private final ProductRepository _productRepository;
    private final CartItemRepository _cartItemRepository;
    private final UserRepository _userRepository;


    //!<<<<<<<<<<<<< CATEGORY RELATED METHODS >>>>>>>>>>>>>>>>//

    /**
     * Get category by name - This can be used in any service to get the category by name
     *
     * @param name name of the category
     * @return Category if found, null if not found returns null
     */
    @Override
    public Category getCategoryByName(String name) {
        try {
            return _categoryRepository.findByName(name);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Save category - This can be used in any service to save the category
     *
     * @param category category object
     * @return Category
     */
    @Override
    public Category saveCategory(Category category) {
        try {
            return _categoryRepository.save(category);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    //!<<<<<<<<<<<<< PRODUCT RELATED METHODS >>>>>>>>>>>>>>>>//

    /**
     * Save a product - for internal use in cart item service
     *
     * @param product The product object
     * @return The saved product
     */
    @Override
    public Product saveProduct(Product product) {
        try {
            return _productRepository.save(product);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Get a product by id - for internal use in cart item service
     *
     * @param productId The id of the product
     * @return The product if found, null if not found returns null
     */
    @Override
    public Product getProductById(Long productId) {
        try {
            return _productRepository.findById(productId).orElse(null);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    //!<<<<<<<<<<<<< CART RELATED METHODS >>>>>>>>>>>>>>>>//

    /**
     * Get a cart by id - for internal use in cart item service
     *
     * @param cartId The id of the cart
     * @return The cart if found, null if not found returns null
     */
    @Override
    public Cart getCartById(Long cartId) {
        try {
            return _cartRepository.findById(cartId).orElse(null);
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
            return _cartRepository.findByUserId(userId);
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
            cart.setAppUser(appUser);
            return _cartRepository.save(cart);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Find cart by user id - for internal use in order service
     *
     * @param userId The id of the user
     * @return The cart if found, null if not found returns null
     */
    @Override
    public Cart findCartByUserId(Long userId) {
        try {
            return _cartRepository.findByUserId(userId);
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


    /**
     * Save a cart - for internal use in cart item service
     *
     * @param cart The cart object
     * @return The saved cart
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


    //!<<<<<<<<<<<<< USER RELATED METHODS >>>>>>>>>>>>>>>>//
    @Override
    public AppUser getUserById(Long userId) {
        try {
            return _userRepository.findById(userId).orElse(null);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
