package com.sasindu.shoppingcart.abstractions.interfaces;

import com.sasindu.shoppingcart.abstractions.dto.request.product.AddProductRequestDto;
import com.sasindu.shoppingcart.abstractions.dto.request.product.UpdateProductRequestDto;
import com.sasindu.shoppingcart.models.Product;

import java.util.List;
import java.util.Map;

/**
 * Interface for Product Service.
 * Provides methods to manage products in the shopping cart.
 */
public interface IProductService {

    /**
     * Add a new product.
     *
     * @param addProductRequest Request object containing product details.
     * @return Product object containing the added product details.
     */
    Product addProduct(AddProductRequestDto addProductRequest);


    /**
     * Get all products.
     *
     * @return List of Product objects containing product details.
     */
    List<Product> getAllProducts();


    /**
     * Get a product by its ID.
     *
     * @param id ID of the product.
     * @return Product object containing the product details.
     */
    Product getProductById(Long id);


    /**
     * Update a product.
     *
     * @param product   Product object containing the updated product details.
     * @param productId ID of the product to be updated.
     * @return Product object containing the updated product details.
     */
    Product updateProduct(UpdateProductRequestDto product, Long productId);


    /**
     * Delete a product.
     *
     * @param id ID of the product to be deleted.
     */
    void deleteProduct(Long id);


    /**
     * Get filtered products.
     *
     * @param filters Map of filters (category, brand and name).
     * @return List of Product objects containing product details.
     */
    List<Product> getFilteredProducts(Map<String, String> filters);


    /**
     * Count products by filters.
     *
     * @param filters Map of filters (category, brand and name).
     * @return Number of products.
     */
    Long countProducts(Map<String, String> filters);


    /**
     * Save product.
     *
     * @param product Product object containing the product details.
     * @return Product object containing the saved product details.
     */
    Product saveProduct(Product product);
}
