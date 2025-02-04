package com.sasindu.shoppingcart.services;


import com.sasindu.shoppingcart.abstractions.ICartItemService;
import com.sasindu.shoppingcart.exceptions.NotFoundException;
import com.sasindu.shoppingcart.models.Cart;
import com.sasindu.shoppingcart.models.CartItem;
import com.sasindu.shoppingcart.models.Product;
import com.sasindu.shoppingcart.repository.CartItemRepository;
import com.sasindu.shoppingcart.repository.CartRepository;
import com.sasindu.shoppingcart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        return _cartRepository.findById(cartId)
                .orElseThrow(() -> new NotFoundException("Cart not found"));
    }


    /**
     * Get a product by id - for internal use in cart item service
     *
     * @param productId The id of the product
     * @return The product
     * @throws NotFoundException if the product is not found
     */
    private Product getProductById(Long productId) {
        return _productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));
    }

    /**
     * Add an item to the cart
     *
     * @param cartId    The id of the cart
     * @param productId The id of the product
     * @param quantity  The quantity of the product
     */
    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        try {
            // 1. get the cart
            // 2. get the product
            // 3. check if product is already in the cart, if so increment the quantity
            // 4. if not, add the product to the cart
            Cart cart = getCartById(cartId);
            Product product = getProductById(productId);

            cart.getCartItems().stream()
                    .filter(cartItem -> cartItem.getProduct().getId().equals(productId))
                    .findFirst()
                    .ifPresentOrElse(item -> {
                        item.setQuantity(item.getQuantity() + quantity);
                        item.setTotalPrice();
                        cart.updateTotalAmount();
                        _cartRepository.save(cart);
                        _cartItemRepository.save(item);
                    }, () -> {
                        CartItem cartItem = new CartItem();
                        cartItem.setCart(cart);
                        cartItem.setProduct(product);
                        cartItem.setQuantity(quantity);
                        cartItem.setUnitPrice(product.getPrice());
                        cartItem.setTotalPrice();
                        cart.addCartItem(cartItem);
                        cart.updateTotalAmount();
                        _cartRepository.save(cart);
                        _cartItemRepository.save(cartItem);
                    });

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
     * Delete all cart items by cart id
     *
     * @param cartId the id of the cart
     */
    @Override
    public void deleteAllByCartId(Long cartId) {
        try {
            _cartItemRepository.deleteAllByCartId(cartId);
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
