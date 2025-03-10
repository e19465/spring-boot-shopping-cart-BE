package com.sasindu.shoppingcart.repository;

import com.sasindu.shoppingcart.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    /**
     * Delete all cart items by cart id
     *
     * @param id the id of the cart
     */
    void deleteAllByCartId(Long id);

    /**
     * Find a cart item by cart id and product id
     *
     * @param cartId    the id of the cart
     * @param productId the id of the product
     * @return the cart item
     */
    CartItem findByCartIdAndProductId(Long cartId, Long productId);


    /**
     * Find all cart items by cart id
     *
     * @param userId the id of the user
     * @return list of cart items
     */
    List<CartItem> findAllByCartUserId(Long userId);


    /**
     * Find all cart items by cart id
     *
     * @param cartId the id of the cart
     * @return list of cart items
     */
    List<CartItem> findAllByCartId(Long cartId);
}
