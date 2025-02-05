package com.sasindu.shoppingcart.services;

import com.sasindu.shoppingcart.abstractions.interfaces.ISharedService;
import com.sasindu.shoppingcart.models.Cart;
import com.sasindu.shoppingcart.models.Category;
import com.sasindu.shoppingcart.models.Product;
import com.sasindu.shoppingcart.repository.CartItemRepository;
import com.sasindu.shoppingcart.repository.CartRepository;
import com.sasindu.shoppingcart.repository.CategoryRepository;
import com.sasindu.shoppingcart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


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

    //!<<<<<<<<<<<<< CATEGORY RELATED METHODS >>>>>>>>>>>>>>>>//

    /**
     * Get category by id - This can be used in any service to get the category by id
     *
     * @param id id of the category
     * @return Category if found, null if not found returns null
     */
    @Override
    public Category getCategoryById(Long id) {
        try {
            return _categoryRepository.findById(id).orElse(null);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


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


    //!<<<<<<<<<<<<< CART ITEM RELATED METHODS >>>>>>>>>>>>>>>>//

    /**
     * Delete all cart items by cart id - for internal use in cart item service
     *
     * @param cartId The id of the cart
     */
    @Override
    public void deleteAllCartItemsByCartId(Long cartId) {
        try {
            _cartItemRepository.deleteAllByCartId(cartId);
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
     * Delete cart by id - for internal use in order service
     *
     * @param cartId The id of the cart
     */
    @Override
    public void deleteCartById(Long cartId) {
        try {
            _cartRepository.deleteById(cartId);
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
}
