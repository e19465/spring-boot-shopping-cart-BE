package com.sasindu.shoppingcart.services;

import com.sasindu.shoppingcart.abstractions.interfaces.ISharedService;
import com.sasindu.shoppingcart.repository.*;
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
    private final UserRepository _userRepository;
}
