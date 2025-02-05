package com.sasindu.shoppingcart.abstractions.interfaces;

import com.sasindu.shoppingcart.models.Cart;
import com.sasindu.shoppingcart.models.Category;
import com.sasindu.shoppingcart.models.Product;

public interface ISharedService {
    Category getCategoryById(Long id);

    Category getCategoryByName(String name);

    Category saveCategory(Category category);

    Cart getCartById(Long cartId);

    Cart saveCart(Cart cart);

    Cart findCartByUserId(Long userId);

    void deleteCartById(Long cartId);

    Product getProductById(Long productId);

    Product saveProduct(Product product);

    void deleteAllCartItemsByCartId(Long cartId);
}
