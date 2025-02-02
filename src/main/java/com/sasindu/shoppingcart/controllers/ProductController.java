package com.sasindu.shoppingcart.controllers;


import com.sasindu.shoppingcart.abstractions.IProductService;
import com.sasindu.shoppingcart.constants.ApplicationConstants;
import com.sasindu.shoppingcart.dto.request.product.AddProductRequest;
import com.sasindu.shoppingcart.dto.request.product.UpdateProductRequest;
import com.sasindu.shoppingcart.dto.response.product.ProductResponse;
import com.sasindu.shoppingcart.helpers.ApiResponse;
import com.sasindu.shoppingcart.helpers.GlobalExceptionHandler;
import com.sasindu.shoppingcart.helpers.GlobalSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * ProductController class is responsible for handling all the API requests related to the product
 */
@RestController
@RequestMapping(ApplicationConstants.API_URL_PREFIX + "/product")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService _productService;


    /**
     * saveProduct method is responsible for saving a product
     * this method calls the addProduct method of the ProductService class internally
     *
     * @param request AddProductRequest object containing the product details
     * @return ApiResponse object containing the response details
     */
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> saveProduct(@RequestBody AddProductRequest request) {
        try {
            ProductResponse product = _productService.addProduct(request);
            return GlobalSuccessHandler.handleSuccess("Product saved successfully", product, 200, null);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e, "Product save failed");
        }
    }


    /**
     * getAllProducts method is responsible for fetching all the products
     * this method calls the getAllProducts method of the ProductService class internally
     *
     * @return ApiResponse object containing the response details
     */
    @PostMapping("/get-all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        try {
            List<ProductResponse> products = _productService.getAllProducts();
            return GlobalSuccessHandler.handleSuccess("Products fetched successfully", products, 200, null);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e, "Products fetch failed");
        }
    }


    /**
     * getProductById method is responsible for fetching a product by its id
     * this method calls the getProductById method of the ProductService class internally
     *
     * @param id Long value of the product id
     * @return ApiResponse object containing the response details
     */
    @PostMapping("/find-by-id/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
        try {
            ProductResponse product = _productService.getProductById(id);
            return GlobalSuccessHandler.handleSuccess("Product fetched successfully", product, 200, null);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e, "Product fetch failed");
        }
    }


    /**
     * updateProduct method is responsible for updating a product
     *
     * @param request UpdateProductRequest object containing the updated product details
     * @param id      Long value of the product id
     * @return ApiResponse object containing the response details
     */
    @PostMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody UpdateProductRequest request, @PathVariable Long id) {
        try {
            ProductResponse product = _productService.updateProduct(request, id);
            return GlobalSuccessHandler.handleSuccess("Product updated successfully", product, 200, null);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e, "Product update failed");
        }
    }


    /**
     * deleteProduct method is responsible for deleting a product
     * this method calls the deleteProduct method of the ProductService class internally
     *
     * @param id Long value of the product id
     * @return ApiResponse object containing the response details
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
        try {
            _productService.deleteProduct(id);
            return GlobalSuccessHandler.handleSuccess("Product deleted successfully", null, 200, null);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e, "Product delete failed");
        }
    }


    /**
     * getProductsByCategory method is responsible for fetching all the products by category
     * this method calls the getProductsByCategory method of the ProductService class internally
     *
     * @param category String value of the category
     * @return ApiResponse object containing the response details
     */
    @PostMapping("/find-by-category/{category}")
    public ResponseEntity<ApiResponse> getProductsByCategory(@PathVariable String category) {
        try {
            List<ProductResponse> products = _productService.getProductsByCategory(category);
            return GlobalSuccessHandler.handleSuccess("Products fetched successfully", products, 200, null);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e, "Products fetch failed");
        }
    }


    /**
     * getProductsByBrand method is responsible for fetching all the products by brand
     * this method calls the getProductsByBrand method of the ProductService class internally
     *
     * @param brand String value of the brand
     * @return ApiResponse object containing the response details
     */
    @PostMapping("/find-by-brand/{brand}")
    public ResponseEntity<ApiResponse> getProductsByBrand(@PathVariable String brand) {
        try {
            List<ProductResponse> products = _productService.getProductsByBrand(brand);
            return GlobalSuccessHandler.handleSuccess("Products fetched successfully", products, 200, null);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e, "Products fetch failed");
        }
    }


    /**
     * getProductByCategoryAndBrand method is responsible for fetching all the products by category and brand
     * this method calls the getProductByCategoryAndBrand method of the ProductService class internally
     *
     * @param category String value of the category
     * @param brand    String value of the brand
     * @return ApiResponse object containing the response details
     */
    @PostMapping("/find-by-category-and-brand/{category}/{brand}")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@PathVariable String category, @PathVariable String brand) {
        try {
            List<ProductResponse> products = _productService.getProductByCategoryAndBrand(category, brand);
            return GlobalSuccessHandler.handleSuccess("Products fetched successfully", products, 200, null);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e, "Products fetch failed");
        }
    }


    /**
     * getProductsByName method is responsible for fetching all the products by name
     * this method calls the getProductsByName method of the ProductService class internally
     *
     * @param name String value of the product name
     * @return ApiResponse object containing the response details
     */
    @PostMapping("/find-by-name/{name}")
    public ResponseEntity<ApiResponse> getProductsByName(@PathVariable String name) {
        try {
            List<ProductResponse> products = _productService.getProductsByName(name);
            return GlobalSuccessHandler.handleSuccess("Products fetched successfully", products, 200, null);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e, "Products fetch failed");
        }
    }


    /**
     * getProductsByBrandAndName method is responsible for fetching all the products by brand and name
     * this method calls the getProductsByBrandAndName method of the ProductService class internally
     *
     * @param brand String value of the brand
     * @param name  String value of the product name
     * @return ApiResponse object containing the response details
     */
    @PostMapping("/find-by-brand-and-name/{brand}/{name}")
    public ResponseEntity<ApiResponse> getProductsByBrandAndName(@PathVariable String brand, @PathVariable String name) {
        try {
            List<ProductResponse> products = _productService.getProductsByBrandAndName(brand, name);
            return GlobalSuccessHandler.handleSuccess("Products fetched successfully", products, 200, null);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e, "Products fetch failed");
        }
    }


    /**
     * countProductsByBrandAndName method is responsible for counting all the products by brand and name
     * this method calls the countProductsByBrandAndName method of the ProductService class internally
     *
     * @param brand String value of the brand
     * @param name  String value of the product name
     * @return ApiResponse object containing the response details
     */
    @PostMapping("/count-by-brand-and-name/{brand}/{name}")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@PathVariable String brand, @PathVariable String name) {
        try {
            Long count = _productService.countProductsByBrandAndName(brand, name);
            return GlobalSuccessHandler.handleSuccess("Products counted successfully", count, 200, null);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e, "Products count failed");
        }
    }

}

/*
 * ENDPOINTS
 * 1. create - POST - http://localhost:9091/api/v1/product/create
 * 2. get-all - POST - http://localhost:9091/api/v1/product/get-all
 * 3. find-by-id - POST - http://localhost:9091/api/v1/product/find-by-id/{id}
 * 4. update - POST - http://localhost:9091/api/v1/product/update/{id}
 * 5. delete - DELETE - http://localhost:9091/api/v1/product/delete/{id}
 * 6. find-by-category - POST - http://localhost:9091/api/v1/product/find-by-category/{category}
 * 7. find-by-brand - POST - http://localhost:9091/api/v1/product/find-by-brand/{brand}
 * 8. find-by-category-and-brand - POST - http://localhost:9091/api/v1/product/find-by-category-and-brand/{category}/{brand}
 * 9. find-by-name - POST - http://localhost:9091/api/v1/product/find-by-name/{name}
 * 10. find-by-brand-and-name - POST - http://localhost:9091/api/v1/product/find-by-brand-and-name/{brand}/{name}
 * 11. count-by-brand-and-name - POST - http://localhost:9091/api/v1/product/count-by-brand-and-name/{brand}/{name}
 * */