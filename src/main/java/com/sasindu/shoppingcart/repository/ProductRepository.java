package com.sasindu.shoppingcart.repository;

import com.sasindu.shoppingcart.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for Product.
 * Provides methods to manage products in the shopping cart.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Find a product by its brand and name.
     *
     * @param brand Brand of the product.
     * @param name  Name of the product.
     * @return List of Product objects containing the product details.
     */
    List<Product> findByBrandAndName(String brand, String name);


    /**
     * Find a product by its name.
     *
     * @param name Name of the product.
     * @return List of Product objects containing the product details.
     */
    List<Product> findByName(String name);


    /**
     * Find products by category and brand.
     *
     * @param category Category of the products.
     * @param brand    Brand of the products.
     * @return List of Product objects containing the product details.
     */
    List<Product> findByCategoryNameAndBrand(String category, String brand);


    /**
     * Find products by brand.
     *
     * @param brand Brand of the products.
     * @return List of Product objects containing the product details.
     */
    List<Product> findByBrand(String brand);


    /**
     * Find products by category.
     *
     * @param category Category of the products.
     * @return List of Product objects containing the product details.
     */
    List<Product> findByCategoryName(String category);


    /**
     * Count products by brand and name.
     *
     * @param brand Brand of the products.
     * @param name  Name of the products.
     * @return Number of products with the given brand and name.
     */
    Long countByBrandAndName(String brand, String name);
}
