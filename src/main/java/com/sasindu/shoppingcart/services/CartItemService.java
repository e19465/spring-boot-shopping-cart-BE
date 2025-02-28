package com.sasindu.shoppingcart.services;


import com.sasindu.shoppingcart.abstractions.dto.request.cartitem.AddCartItemRequestDto;
import com.sasindu.shoppingcart.abstractions.dto.request.cartitem.UpdateCartItemRequestDto;
import com.sasindu.shoppingcart.abstractions.interfaces.*;
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
    private final ICartService _cartService;
    private final IUserService _userService;
    private final IProductService _productService;
    private final IAuthService _authService;


    /**
     * Get the cart by id - This is for internal use only
     *
     * @param cartId the id of the cart
     * @return the cart
     */
    private Cart getCartById(Long cartId) {
        try {
            Cart cart = _cartService.getCartById(cartId);
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
            AppUser user = _authService.getAuthenticatedUser();
            CartItem cartItem = _cartItemRepository.findByCartIdAndProductId(cartId, productId);
            if (cartItem == null) {
                throw new NotFoundException("Cart item not found");
            }
            if (!cartItem.getCart().getAppUser().getId().equals(user.getId())) {
                throw new UnAuthorizedException("Unauthorized access");
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
     * @param request The AddCartItemRequestDto request object
     */
    @Override
    @Transactional
    public void addItemToCart(AddCartItemRequestDto request) {
        try {
            // 1. Retrieve the cart and product, if cart not found, create new cart
            AppUser user = _authService.getAuthenticatedUser();
            Long userId = user.getId();
            Long productId = request.getProductId();
            int quantity = request.getQuantity();
            Cart cart = _cartService.getCartByUserId(userId);
            if (cart == null) {
                AppUser foundAppUser = _userService.getUserById(userId);
                if (foundAppUser == null) {
                    throw new NotFoundException("User not found");
                }
                cart = _cartService.initializeNewCart(foundAppUser);
            }

            Product product = _productService.getProductById(productId);
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
            _cartService.saveCart(cart);  // Cascade will save CartItems if mapped correctly
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
            AppUser user = _authService.getAuthenticatedUser();

            // get the cart
            Cart cart = getCartById(cartId);

            // check if the cart belongs to the user
            if (!cart.getAppUser().getId().equals(user.getId())) {
                throw new UnAuthorizedException("Unauthorized access");
            }


            // get the product
            CartItem cartItem = getCartItemByCartIdAndProductId(cartId, productId);

            cart.removeCartItem(cartItem);
            _cartItemRepository.delete(cartItem);
            cart.updateTotalAmount();
            _cartService.saveCart(cart);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Update the quantity of an item in the cart
     *
     * @param request The UpdateCartItemRequestDto request object
     */
    @Override
    public void updateItemQuantity(UpdateCartItemRequestDto request) {
        try {
            Long cartId = request.getCartId();
            Long productId = request.getProductId();
            int quantity = request.getQuantity();

            AppUser user = _authService.getAuthenticatedUser();

            // get the cart
            Cart cart = getCartById(cartId);

            // check if the cart belongs to the user
            if (!cart.getAppUser().getId().equals(user.getId())) {
                throw new UnAuthorizedException("Unauthorized access");
            }

            // get the product
            CartItem cartItem = getCartItemByCartIdAndProductId(cartId, productId);

            cartItem.setQuantity(quantity);
            cartItem.setTotalPrice();
            cart.updateTotalAmount();
            _cartService.saveCart(cart);
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
            AppUser appUser = _authService.getAuthenticatedUser();
            boolean isAdmin = _authService.isAuthenticatedUserAdmin();

            if (!isAdmin && !appUser.getId().equals(userId)) {
                throw new UnAuthorizedException("Unauthorized access");
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
    public List<CartItem> getCartItemsByCartId(Long cartId) {
        try {
            Cart cart = _cartService.getCartById(cartId);
            return _cartItemRepository.findAllByCartId(cart.getId());
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Delete all cart items by cart id
     *
     * @param id The id of the cart
     */
    @Override
    public void deleteAllByCartId(Long id) {
        try {
            Cart cart = getCartById(id);
            _cartItemRepository.deleteAllByCartId(cart.getId());
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
