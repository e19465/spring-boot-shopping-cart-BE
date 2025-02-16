package com.sasindu.shoppingcart.services;


import com.sasindu.shoppingcart.abstractions.interfaces.ICartItemService;
import com.sasindu.shoppingcart.abstractions.interfaces.ISharedService;
import com.sasindu.shoppingcart.exceptions.BadRequestException;
import com.sasindu.shoppingcart.exceptions.NotFoundException;
import com.sasindu.shoppingcart.exceptions.UnAuthorizedException;
import com.sasindu.shoppingcart.models.AppUser;
import com.sasindu.shoppingcart.models.Cart;
import com.sasindu.shoppingcart.models.CartItem;
import com.sasindu.shoppingcart.models.Product;
import com.sasindu.shoppingcart.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * CartItemService class is responsible for handling the business logic related to the cart item
 */
@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private final CartItemRepository _cartItemRepository;
    private final ISharedService _sharedService;


    /**
     * Get the cart by id - This is for internal use only
     *
     * @param cartId the id of the cart
     * @return the cart
     */
    private Cart getCartById(Long cartId) {
        try {
            Cart cart = _sharedService.getCartById(cartId);
            if (cart == null) {
                throw new NotFoundException("Cart not found");
            }
            return cart;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Get the cart item by cart id and product id - This is for internal use only
     *
     * @param cartId    the id of the cart
     * @param productId the id of the product
     * @return the cart item
     */
    private CartItem getCartItemByCartIdAndProductId(Long cartId, Long productId) {
        try {
            CartItem cartItem = _cartItemRepository.findByCartIdAndProductId(cartId, productId);
            if (cartItem == null) {
                throw new NotFoundException("Cart item not found");
            }
            return cartItem;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Add an item to the cart
     *
     * @param userId    The id of the user who owns the cart
     * @param productId The id of the product
     * @param quantity  The quantity of the product
     */
    @Override
    @Transactional
    public void addItemToCart(Long userId, Long productId, int quantity) {
        try {
            // 1. Retrieve the cart and product, if cart not found, create new cart
            Cart cart = _sharedService.getCartByUserId(userId);
            if (cart == null) {
                AppUser foundAppUser = _sharedService.getUserById(userId);
                if (foundAppUser == null) {
                    throw new NotFoundException("User not found");
                }
                cart = _sharedService.initializeNewCart(foundAppUser);
            }

            Product product = _sharedService.getProductById(productId);
            if (product == null) {
                throw new NotFoundException("Product not found");
            }


            // check if the inventory is enough for the product
            if (product.getInventory() < quantity) {
                throw new BadRequestException("Inventory is not enough for product: " + product.getName());
            }

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
            _sharedService.saveCart(cart);  // Cascade will save CartItems if mapped correctly
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

            // get the cart
            Cart cart = getCartById(cartId);

            // get the product
            CartItem cartItem = getCartItemByCartIdAndProductId(cartId, productId);

            cart.removeCartItem(cartItem);
            _cartItemRepository.delete(cartItem);
            cart.updateTotalAmount();
            _sharedService.saveCart(cart);
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
            // get the cart
            Cart cart = getCartById(cartId);

            // get the product
            CartItem cartItem = getCartItemByCartIdAndProductId(cartId, productId);

            cartItem.setQuantity(quantity);
            cartItem.setTotalPrice();
            cart.updateTotalAmount();
            _sharedService.saveCart(cart);
            _cartItemRepository.save(cartItem);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Get the cart items by user id
     *
     * @param userId The id of the user
     * @return List of cart items
     */
    @Override
    public List<CartItem> getCartItemsByUserId(Long userId) {
        try {
            AppUser appUser = _sharedService.getUserById(userId);
            if (appUser == null) {
                throw new NotFoundException("User not found");
            }
            return _cartItemRepository.findAllByCartUserId(userId);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Get the cart items by cart id
     *
     * @param cartId The id of the cart
     * @return List of cart items
     */
    @Override
    public List<CartItem> getCartItemsByCartId(Long cartId, Long userId) {
        try {
            Cart cart = _sharedService.getCartById(cartId);
            if (cart == null) {
                throw new NotFoundException("Cart not found");
            }

            if (!cart.getAppUser().getId().equals(userId)) {
                throw new UnAuthorizedException("Unauthorized access");
            }

            return _cartItemRepository.findAllByCartId(cartId);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
