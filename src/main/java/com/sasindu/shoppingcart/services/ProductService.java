package com.sasindu.shoppingcart.services;

import com.sasindu.shoppingcart.abstractions.dto.request.product.AddProductRequestDto;
import com.sasindu.shoppingcart.abstractions.dto.request.product.UpdateProductRequestDto;
import com.sasindu.shoppingcart.abstractions.interfaces.ICategoryService;
import com.sasindu.shoppingcart.abstractions.interfaces.IProductService;
import com.sasindu.shoppingcart.exceptions.NotFoundException;
import com.sasindu.shoppingcart.models.Category;
import com.sasindu.shoppingcart.models.Product;
import com.sasindu.shoppingcart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Service class for managing products implements IProductService.
 */
@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    // Inject the product repository through the constructor
    // In here, we don't define constructor explicitly,
    // because Lombok's @RequiredArgsConstructor will do it for us
    private final ProductRepository _productRepository;
    private final ICategoryService _categoryService;

    /**
     * Add a new product.
     *
     * @param request Request of type AddProductRequest object containing product details.
     * @return Product object containing the added product details.
     */
    @Override
    public Product addProduct(AddProductRequestDto request) {
        // check if the category exists in the database
        // idf yes, use it else create a new category
        try {
            // check if the category already exists
            Category category = _categoryService.getCategoryByName(request.getCategory().getName());
            if (category == null) {
                Category newCategory = new Category(request.getCategory().getName());
                category = _categoryService.saveCategory(newCategory);
            }

            // set the category to the product
            request.setCategory(category);

            // save the product
            Product newProduct = new Product(
                    request.getName(),
                    request.getBrand(),
                    request.getPrice(),
                    request.getInventory(),
                    request.getDescription(),
                    category
            );
            return _productRepository.save(newProduct);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to add product: " + e.getMessage(), e);
        }
    }


    /**
     * Update a product.
     *
     * @param request   UpdateProductRequest object containing the updated product details.
     * @param productId Long ID of the product to be updated.
     * @return Product object containing the updated product details.
     * @throws NotFoundException if the product is not found.
     */
    @Override
    public Product updateProduct(UpdateProductRequestDto request, Long productId) {
        try {
            // Check if the product exists
            return _productRepository.findById(productId)
                    .map(existingProduct -> {
                        existingProduct.setName(request.getName());
                        existingProduct.setBrand(request.getBrand());
                        existingProduct.setPrice(request.getPrice());
                        existingProduct.setInventory(request.getInventory());
                        existingProduct.setDescription(request.getDescription());
                        Category category = _categoryService.getCategoryByName(request.getCategory().getName());
                        if (category == null) {
                            throw new NotFoundException("Category not found");
                        }
                        existingProduct.setCategory(category);
                        return existingProduct;
                    })
                    .map(_productRepository::save)
                    .orElseThrow(() -> new NotFoundException("Product not found"));
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update product: " + e.getMessage(), e);
        }
    }


    /**
     * Get all products.
     *
     * @return List of Product objects containing product details.
     */
    @Override
    public List<Product> getAllProducts() {
        try {
            return _productRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch products: " + e.getMessage(), e);
        }
    }


    /**
     * Get a product by its ID.
     *
     * @param id Long ID of the product.
     * @return Product object containing the product details.
     * @throws NotFoundException if the product is not found.
     */
    @Override
    public Product getProductById(Long id) {
        try {
            return _productRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Product not found"));
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch product: " + e.getMessage(), e);
        }
    }


    /**
     * Delete a product.
     *
     * @param id Long ID of the product to be deleted.
     * @throws NotFoundException if the product is not found.
     */
    @Override
    public void deleteProduct(Long id) {
        try {
            _productRepository.findById(id)
                    .ifPresentOrElse(_productRepository::delete, () -> {
                        throw new NotFoundException("Product not found");
                    });
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete product: " + e.getMessage(), e);
        }
    }


    /**
     * Get filtered products.
     *
     * @param filters A map containing all the filter parameters (category, brand, name, etc.)
     * @return List of Product objects containing the filtered products.
     */
    @Override
    public List<Product> getFilteredProducts(Map<String, String> filters) {
        try {
            String category = filters.get("category");
            String brand = filters.get("brand");
            String name = filters.get("name");

            // Use a stream to filter based on available parameters
            return _productRepository.findAll().stream()
                    .filter(product -> (category == null || product.getCategory().getName().equals(category)))
                    .filter(product -> (brand == null || product.getBrand().equals(brand)))
                    .filter(product -> (name == null || product.getName().contains(name)))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to filter products: " + e.getMessage(), e);
        }
    }


    /**
     * Count products by brand.
     *
     * @param filters A map containing all the filter parameters (category, brand, name, etc.)
     * @return Number of products.
     */
    @Override
    public Long countProducts(Map<String, String> filters) {
        String category = filters.get("category");
        String brand = filters.get("brand");
        String name = filters.get("name");
        try {
            return _productRepository.findAll().stream()
                    .filter(product -> (category == null || product.getCategory().getName().equals(category)))
                    .filter(product -> (brand == null || product.getBrand().equals(brand)))
                    .filter(product -> (name == null || product.getName().contains(name)))
                    .count();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Save a product.
     *
     * @param product Product object containing the product details.
     * @return Product object containing the saved product details.
     */
    @Override
    public Product saveProduct(Product product) {
        try {
            return _productRepository.save(product);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save product: " + e.getMessage(), e);
        }
    }
}
