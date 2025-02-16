package com.sasindu.shoppingcart.abstractions.interfaces;

import com.sasindu.shoppingcart.models.AppUser;
import com.sasindu.shoppingcart.models.Cart;
import com.sasindu.shoppingcart.models.Category;
import com.sasindu.shoppingcart.models.Product;

public interface ISharedService {

    //! CATEGORY related methods //

    /**
     * Get a category by its name
     *
     * @param name The name of the category
     * @return The category object
     */
    Category getCategoryByName(String name);


    /**
     * Save a category
     *
     * @param category The category object
     * @return The saved category object
     */
    Category saveCategory(Category category);


    //! CART related methods //

    /**
     * Initialize a new cart
     *
     * @param appUser The user to initialize the cart for
     * @return The new cart
     */
    public Cart initializeNewCart(AppUser appUser);

    /**
     * Get a cart by its id
     *
     * @param cartId The id of the cart
     * @return The cart object
     */
    Cart getCartById(Long cartId);


    /**
     * Get a cart by the user id
     *
     * @param userId The id of the user
     * @return The cart object
     */
    Cart getCartByUserId(Long userId);

    /**
     * Save a cart
     *
     * @param cart The cart object
     * @return The saved cart object
     */
    Cart saveCart(Cart cart);


    /**
     * Get a cart by the user id
     *
     * @param userId The id of the user
     * @return The cart object
     */
    Cart findCartByUserId(Long userId);


    /**
     * Delete a cart by the cart object
     *
     * @param cart The cart object
     */
    void clearCartByCart(Cart cart);

    //! PRODUCT related methods //

    /**
     * Get a product by its id
     *
     * @param productId The id of the product
     * @return The product object
     */
    Product getProductById(Long productId);


    /**
     * Save a product
     *
     * @param product The product object
     * @return The saved product object
     */
    Product saveProduct(Product product);


    //! USER related methods //

    /**
     * Get a user by its id
     *
     * @param userId The id of the user
     * @return The user object
     */
    AppUser getUserById(Long userId);
}
