package com.sasindu.shoppingcart.services;


import com.sasindu.shoppingcart.abstractions.interfaces.ICartItemService;
import com.sasindu.shoppingcart.exceptions.NotFoundException;
import com.sasindu.shoppingcart.models.Cart;
import com.sasindu.shoppingcart.models.CartItem;
import com.sasindu.shoppingcart.models.Product;
import com.sasindu.shoppingcart.repository.CartItemRepository;
import com.sasindu.shoppingcart.repository.CartRepository;
import com.sasindu.shoppingcart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private final CartItemRepository _cartItemRepository;
    private final CartRepository _cartRepository;
    private final ProductRepository _productRepository;


    /**
     * Get a cart by id - for internal use in cart item service
     *
     * @param cartId The id of the cart
     * @return The cart
     * @throws NotFoundException if the cart is not found
     */
    private Cart getCartById(Long cartId) {
        try {
            return _cartRepository.findById(cartId)
                    .orElseThrow(() -> new NotFoundException("Cart not found"));
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
     * @return The product
     * @throws NotFoundException if the product is not found
     */
    private Product getProductById(Long productId) {
        try {
            return _productRepository.findById(productId)
                    .orElseThrow(() -> new NotFoundException("Product not found"));
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Add an item to the cart
     *
     * @param cartId    The id of the cart
     * @param productId The id of the product
     * @param quantity  The quantity of the product
     */
    @Override
    @Transactional
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        try {
            // 1. Retrieve the cart and product
            Cart cart = getCartById(cartId);
            Product product = getProductById(productId);

            // 2. Check if the product is already in the cart
            CartItem existingCartItem = cart.getCartItems()
                    .stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst()
                    .orElse(null);

            if (existingCartItem != null) {
                // 3. If the product exists, update quantity and total price
                existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
                existingCartItem.setTotalPrice();
            } else {
                // 4. If not, create a new cart item and add it to the cart
                CartItem newCartItem = new CartItem();
                newCartItem.setCart(cart);
                newCartItem.setProduct(product);
                newCartItem.setQuantity(quantity);
                newCartItem.setUnitPrice(product.getPrice());
                newCartItem.setTotalPrice();
                cart.addCartItem(newCartItem);
            }

            // 5. Update the cart's total amount and persist changes
            cart.updateTotalAmount();
            _cartRepository.save(cart);  // Cascade will save CartItems if mapped correctly
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Remove an item from the cart
     *
     * @param cartId    The id of the cart
     * @param productId The id of the product
     */
    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        try {
            Cart cart = getCartById(cartId);
            CartItem cartItem = _cartItemRepository.findByCartIdAndProductId(cartId, productId);

            if (cartItem == null) {
                throw new NotFoundException("Cart item not found");
            }

            cart.removeCartItem(cartItem);
            _cartItemRepository.delete(cartItem);
            cart.updateTotalAmount();
            _cartRepository.save(cart);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Update the quantity of an item in the cart
     *
     * @param cartId    The id of the cart
     * @param productId The id of the product
     * @param quantity  The new quantity
     */
    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        try {
            Cart cart = getCartById(cartId);
            CartItem cartItem = _cartItemRepository.findByCartIdAndProductId(cartId, productId);

            if (cartItem == null) {
                throw new NotFoundException("Cart item not found");
            }

            cartItem.setQuantity(quantity);
            cartItem.setTotalPrice();
            cart.updateTotalAmount();
            _cartRepository.save(cart);
            _cartItemRepository.save(cartItem);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Save a cart item - This is for internal use
     *
     * @param cartItem cart item to be saved
     * @return the saved cart item
     */
    @Override
    public CartItem saveCartItem(CartItem cartItem) {
        try {
            return _cartItemRepository.save(cartItem);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
