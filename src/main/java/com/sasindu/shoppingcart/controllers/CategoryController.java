package com.sasindu.shoppingcart.controllers;


import com.sasindu.shoppingcart.abstractions.dto.request.category.AddCategoryRequestDto;
import com.sasindu.shoppingcart.abstractions.dto.request.category.UpdateCategoryRequestDto;
import com.sasindu.shoppingcart.abstractions.dto.response.category.CategoryResponseDto;
import com.sasindu.shoppingcart.abstractions.dto.response.product.ProductResponseDto;
import com.sasindu.shoppingcart.abstractions.interfaces.ICategoryService;
import com.sasindu.shoppingcart.helpers.ApiResponse;
import com.sasindu.shoppingcart.helpers.ErrorResponseHandler;
import com.sasindu.shoppingcart.helpers.SuccessResponseHandler;
import com.sasindu.shoppingcart.helpers.ValidationHelper;
import com.sasindu.shoppingcart.models.Category;
import com.sasindu.shoppingcart.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * CategoryController class is responsible for handling all the API requests related to the category
 */
@RestController
@RequestMapping("${api.prefix}" + "/category")
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService _categoryService;

    /**
     * saveCategory method is responsible for saving a category
     * this method calls the addCategory method of the CategoryService class internally
     * Only users with the role ROLE_ADMIN can access this endpoint
     *
     * @param request AddCategoryRequest object containing the category details
     * @return ApiResponse object containing the response details
     */
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> createCategory(@RequestBody AddCategoryRequestDto request) {
        try {
            // validate the request
            ValidationHelper.validateModelBinding(request);
            CategoryResponseDto category = _categoryService.addCategory(request).toCategoryResponse();
            return SuccessResponseHandler.handleSuccess("Category created successfully", category, HttpStatus.CREATED.value(), null);
        } catch (Exception e) {
            return ErrorResponseHandler.handleException(e);
        }
    }


    /**
     * getCategoryById method is responsible for fetching a category by its id
     * this method calls the getCategoryById method of the CategoryService class internally
     *
     * @param id Long value of the category id
     * @return ApiResponse object containing the response details
     */
    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
        try {
            CategoryResponseDto category = _categoryService.getCategoryById(id).toCategoryResponse();
            return SuccessResponseHandler.handleSuccess("Category fetched successfully", category, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return ErrorResponseHandler.handleException(e);
        }
    }


    /**
     * getCategoryByName method is responsible for fetching a category by its name
     * this method calls the getCategoryByName method of the CategoryService class internally
     *
     * @param name String value of the category name
     * @return ApiResponse object containing the response details
     */
    @GetMapping("/find-by-name/{name}")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name) {
        try {
            CategoryResponseDto category = _categoryService.getCategoryByName(name).toCategoryResponse();
            return SuccessResponseHandler.handleSuccess("Category fetched successfully", category, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return ErrorResponseHandler.handleException(e);
        }
    }


    /**
     * getAllCategories method is responsible for fetching all the categories
     * this method calls the getAllCategories method of the CategoryService class internall
     *
     * @return ApiResponse object containing the response details
     */
    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse> getAllCategories() {
        try {
            List<CategoryResponseDto> categories = _categoryService.getAllCategories().stream().map(Category::toCategoryResponse).toList();
            return SuccessResponseHandler.handleSuccess("Categories fetched successfully", categories, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return ErrorResponseHandler.handleException(e);
        }
    }


    /**
     * updateCategory method is responsible for updating a category
     * this method calls the updateCategory method of the CategoryService class internally
     * Only users with the role ROLE_ADMIN can access this endpoint
     *
     * @param request UpdateCategoryRequest object containing the updated category details
     * @param id      Long value of the category id
     * @return ApiResponse object containing the response details
     */
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> updateCategory(@RequestBody UpdateCategoryRequestDto request, @PathVariable Long id) {
        try {
            // validate the request
            ValidationHelper.validateModelBinding(request);

            CategoryResponseDto category = _categoryService.updateCategory(request, id).toCategoryResponse();
            return SuccessResponseHandler.handleSuccess("Category updated successfully", category, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return ErrorResponseHandler.handleException(e);
        }
    }


    /**
     * deleteCategory method is responsible for deleting a category
     * this method calls the deleteCategoryById method of the CategoryService class internally
     * Only users with the role ROLE_ADMIN can access this endpoint
     *
     * @param id Long value of the category id
     * @return ApiResponse object containing the response details
     */
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        try {
            _categoryService.deleteCategoryById(id);
            return SuccessResponseHandler.handleSuccess("Category deleted successfully", null, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return ErrorResponseHandler.handleException(e);
        }
    }


    /**
     * getAllProductsForCategory method is responsible for fetching all the products for a category
     * this method calls the getAllProductsForCategory method of the CategoryService class internally
     *
     * @param category String value of the category name
     * @return ApiResponse object containing the response details
     */
    @GetMapping("/get-products/{category}")
    public ResponseEntity<ApiResponse> getAllProductsForCategory(@PathVariable String category) {
        try {
            List<ProductResponseDto> products = _categoryService.getAllProductsForCategory(category).stream().map(Product::toProductResponse).toList();
            return SuccessResponseHandler.handleSuccess("All products for category", products, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return ErrorResponseHandler.handleException(e);
        }
    }
}


/*
 * ENDPOINTS
 * 1. create category - POST - http://localhost:9091/api/v1/category/create
 * 2. get category by id - GET - http://localhost:9091/api/v1/category/find-by-id/{id}
 * 3. get category by name - GET - http://localhost:9091/api/v1/category/find-by-name/{name}
 * 4. get all categories - GET - http://localhost:9091/api/v1/category/get-all
 * 5. update category - PUT - http://localhost:9091/api/v1/category/update/{id}
 * 6. delete category - DELETE - http://localhost:9091/api/v1/category/delete/{id}
 * 7. get products for category - GET - http://localhost:9091/api/v1/category/get-products/{category}
 */