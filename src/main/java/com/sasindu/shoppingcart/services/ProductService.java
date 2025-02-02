package com.sasindu.shoppingcart.services;

import com.sasindu.shoppingcart.abstractions.IProductService;
import com.sasindu.shoppingcart.dto.request.product.AddProductRequest;
import com.sasindu.shoppingcart.dto.request.product.UpdateProductRequest;
import com.sasindu.shoppingcart.dto.response.product.ProductResponse;
import com.sasindu.shoppingcart.exceptions.NotFoundException;
import com.sasindu.shoppingcart.models.Category;
import com.sasindu.shoppingcart.models.Product;
import com.sasindu.shoppingcart.repository.CategoryRepository;
import com.sasindu.shoppingcart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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
    private final CategoryRepository _categoryRepository;

    /**
     * Add a new product.
     *
     * @param request Request of type AddProductRequest object containing product details.
     * @return ProductResponse object containing the added product details.
     */
    @Override
    public ProductResponse addProduct(AddProductRequest request) {
        // check if the category exists in the database
        // idf yes, use it else create a new category
        try {
            Category category = Optional.ofNullable(_categoryRepository.findByName(request.getCategory().getName()))
                    .orElseGet(() -> {
                        Category newCategory = new Category(request.getCategory().getName());
                        return _categoryRepository.save(newCategory);
                    });
            request.setCategory(category);
            Product newProduct = new Product(
                    request.getName(),
                    request.getBrand(),
                    request.getPrice(),
                    request.getInventory(),
                    request.getDescription(),
                    category
            );
            return _productRepository.save(newProduct).toProductResponse();
        } catch (Exception e) {
            throw new RuntimeException("Failed to add product: " + e.getMessage(), e);
        }
    }


    /**
     * Update a product.
     *
     * @param request   UpdateProductRequest object containing the updated product details.
     * @param productId Long ID of the product to be updated.
     * @return ProductResponse object containing the updated product details.
     * @throws NotFoundException if the product is not found.
     */
    @Override
    public ProductResponse updateProduct(UpdateProductRequest request, Long productId) {
        try {
            return _productRepository.findById(productId)
                    .map(existingProduct -> {
                        existingProduct.setName(request.getName());
                        existingProduct.setBrand(request.getBrand());
                        existingProduct.setPrice(request.getPrice());
                        existingProduct.setInventory(request.getInventory());
                        existingProduct.setDescription(request.getDescription());
                        Category category = _categoryRepository.findByName(request.getCategory().getName());
                        existingProduct.setCategory(category);
                        return existingProduct;
                    })
                    .map(_productRepository::save)
                    .map(Product::toProductResponse)
                    .orElseThrow(() -> new NotFoundException("Product not found"));
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update product: " + e.getMessage(), e);
        }
    }


    /**
     * Get all products.
     *
     * @return List of ProductResponse objects containing product details.
     */
    @Override
    public List<ProductResponse> getAllProducts() {
        try {
            List<Product> products = _productRepository.findAll();
            return products.stream()
                    .map(Product::toProductResponse).toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch products: " + e.getMessage(), e);
        }
    }


    /**
     * Get a product by its ID.
     *
     * @param id Long ID of the product.
     * @return ProductResponse object containing the product details.
     * @throws NotFoundException if the product is not found.
     */
    @Override
    public ProductResponse getProductById(Long id) {
        try {
            Product product = _productRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Product not found"));
            return product.toProductResponse();
        } catch (NotFoundException e) {
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
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete product: " + e.getMessage(), e);
        }
    }


    /**
     * Get all products by category.
     *
     * @param category Category name of the products.
     * @return List of ProductResponse objects containing product details.
     */
    @Override
    public List<ProductResponse> getProductsByCategory(String category) {
        try {
            List<Product> products = _productRepository.findByCategoryName(category);
            return products.stream()
                    .map(Product::toProductResponse).toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch products: " + e.getMessage(), e);
        }
    }


    /**
     * Get all products by brand.
     *
     * @param brand Brand name of the products.
     * @return List of ProductResponse objects containing product details.
     */
    @Override
    public List<ProductResponse> getProductsByBrand(String brand) {
        try {
            List<Product> products = _productRepository.findByBrandName(brand);
            return products.stream().map(Product::toProductResponse).toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch products: " + e.getMessage(), e);
        }
    }


    /**
     * Get all products by category and brand.
     *
     * @param category Category name of the products.
     * @param brand    Brand name of the products.
     * @return List of ProductResponse objects containing product details.
     */
    @Override
    public List<ProductResponse> getProductByCategoryAndBrand(String category, String brand) {
        try {
            List<Product> products = _productRepository.findByCategoryNameAndBrandName(category, brand);
            return products.stream().map(Product::toProductResponse).toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch products: " + e.getMessage(), e);
        }
    }


    /**
     * Get products by name.
     *
     * @param name Name of the products.
     * @return List of ProductResponse objects containing product details.
     */
    @Override
    public List<ProductResponse> getProductsByName(String name) {
        try {
            List<Product> products = _productRepository.findByName(name);
            return products.stream().map(Product::toProductResponse).toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch products: " + e.getMessage(), e);
        }
    }


    /**
     * Get products by brand and name.
     *
     * @param brand Brand name of the products.
     * @param name  Name of the products.
     * @return List of ProductResponse objects containing product details.
     */
    @Override
    public List<ProductResponse> getProductsByBrandAndName(String brand, String name) {
        try {
            List<Product> products = _productRepository.findByBrandAndName(brand, name);
            return products.stream().map(Product::toProductResponse).toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch products: " + e.getMessage(), e);
        }
    }


    /**
     * Count products by brand and name.
     *
     * @param brand Brand name of the products.
     * @param name  Name of the products.
     * @return Number of products.
     */
    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        try {
            return _productRepository.countByBrandAndName(brand, name);
        } catch (Exception e) {
            throw new RuntimeException("Failed to count products: " + e.getMessage(), e);
        }
    }
}
