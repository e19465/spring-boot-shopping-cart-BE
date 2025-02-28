package com.sasindu.shoppingcart.controllers;


import com.sasindu.shoppingcart.abstractions.dto.request.product.AddProductRequestDto;
import com.sasindu.shoppingcart.abstractions.dto.request.product.UpdateProductRequestDto;
import com.sasindu.shoppingcart.abstractions.dto.response.product.ProductResponseDto;
import com.sasindu.shoppingcart.abstractions.interfaces.IProductService;
import com.sasindu.shoppingcart.helpers.ApiResponse;
import com.sasindu.shoppingcart.helpers.ErrorResponseHandler;
import com.sasindu.shoppingcart.helpers.SuccessResponseHandler;
import com.sasindu.shoppingcart.helpers.ValidationHelper;
import com.sasindu.shoppingcart.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * ProductController class is responsible for handling all the API requests related to the product
 */
@RestController
@RequestMapping("${api.prefix}" + "/product")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService _productService;

    /**
     * saveProduct method is responsible for saving a product
     * this method calls the addProduct method of the ProductService class internally
     * only accessible by admins
     *
     * @param request AddProductRequest object containing the product details
     * @return ApiResponse object containing the response details
     */
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> saveProduct(@RequestBody AddProductRequestDto request) {
        try {
            // validate the request
            ValidationHelper.validateModelBinding(request);

            ProductResponseDto product = _productService.addProduct(request).toProductResponse();
            return SuccessResponseHandler.handleSuccess("Product saved successfully", product, HttpStatus.CREATED.value(), null);
        } catch (Exception e) {
            return ErrorResponseHandler.handleException(e);
        }
    }


    /**
     * getAllProducts method is responsible for fetching all the products
     * this method calls the getAllProducts method of the ProductService class internally
     *
     * @return ApiResponse object containing the response details
     */
    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        try {
            List<ProductResponseDto> products = _productService.getAllProducts().stream().map(Product::toProductResponse).toList();
            return SuccessResponseHandler.handleSuccess("Products fetched successfully", products, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return ErrorResponseHandler.handleException(e);
        }
    }


    /**
     * getProductById method is responsible for fetching a product by its id
     * this method calls the getProductById method of the ProductService class internally
     *
     * @param id Long value of the product id
     * @return ApiResponse object containing the response details
     */
    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
        try {
            ProductResponseDto product = _productService.getProductById(id).toProductResponse();
            return SuccessResponseHandler.handleSuccess("Product fetched successfully", product, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return ErrorResponseHandler.handleException(e);
        }
    }


    /**
     * updateProduct method is responsible for updating a product
     * this method calls the updateProduct method of the ProductService class internally
     * only accessible by admins
     *
     * @param request UpdateProductRequest object containing the updated product details
     * @param id      Long value of the product id
     * @return ApiResponse object containing the response details
     */
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody UpdateProductRequestDto request, @PathVariable Long id) {
        try {
            // validate the request
            ValidationHelper.validateModelBinding(request);

            ProductResponseDto product = _productService.updateProduct(request, id).toProductResponse();
            return SuccessResponseHandler.handleSuccess("Product updated successfully", product, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return ErrorResponseHandler.handleException(e);
        }
    }


    /**
     * deleteProduct method is responsible for deleting a product
     * this method calls the deleteProduct method of the ProductService class internally
     * only accessible by admins
     *
     * @param id Long value of the product id
     * @return ApiResponse object containing the response details
     */
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
        try {
            _productService.deleteProduct(id);
            return SuccessResponseHandler.handleSuccess("Product deleted successfully", null, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return ErrorResponseHandler.handleException(e);
        }
    }


    /**
     * General filter endpoint that accepts various filters like category, brand, name, etc.
     *
     * @param filters A map containing all the filter parameters (category, brand, name, etc.)
     * @return ApiResponse object containing the filtered products
     */
    @GetMapping("/filter")
    public ResponseEntity<ApiResponse> getFilteredProducts(@RequestParam Map<String, String> filters) {
        try {
            List<ProductResponseDto> products = _productService.getFilteredProducts(filters).stream().map(Product::toProductResponse).toList();
            return SuccessResponseHandler.handleSuccess("Products fetched successfully", products, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return ErrorResponseHandler.handleException(e);
        }
    }


    /**
     * Get products by category.
     *
     * @param filters A map containing all the filter parameters (category, brand, name, etc.)
     * @return ApiResponse object containing the filtered products
     */
    @GetMapping("/count")
    public ResponseEntity<ApiResponse> getProductsByCategory(@RequestParam Map<String, String> filters) {
        try {
            Long count = _productService.countProducts(filters);
            return SuccessResponseHandler.handleSuccess("Products fetched successfully", count, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return ErrorResponseHandler.handleException(e);
        }
    }
}

/*
 * ENDPOINTS
 * 1. create - POST - http://localhost:9091/api/v1/product/create
 * 2. get-all - GET - http://localhost:9091/api/v1/product/get-all
 * 3. find-by-id - GET - http://localhost:9091/api/v1/product/find-by-id/{id}
 * 4. update - PUT - http://localhost:9091/api/v1/product/update/{id}
 * 5. delete - DELETE - http://localhost:9091/api/v1/product/delete/{id}
 * 6. filter - GET - http://localhost:9091/api/v1/product/filter/?category={category}&brand={brand}&name={name}
 * 7. count - GET - http://localhost:9091/api/v1/product/count/?category={category}&brand={brand}&name={name}
 */