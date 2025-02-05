package com.sasindu.shoppingcart.controllers;


import com.sasindu.shoppingcart.abstractions.dto.request.category.AddCategoryRequest;
import com.sasindu.shoppingcart.abstractions.dto.request.category.UpdateCategoryRequest;
import com.sasindu.shoppingcart.abstractions.dto.response.category.CategoryResponse;
import com.sasindu.shoppingcart.abstractions.dto.response.product.ProductResponse;
import com.sasindu.shoppingcart.abstractions.interfaces.ICategoryService;
import com.sasindu.shoppingcart.constants.ApplicationConstants;
import com.sasindu.shoppingcart.helpers.ApiResponse;
import com.sasindu.shoppingcart.helpers.GlobalExceptionHandler;
import com.sasindu.shoppingcart.helpers.GlobalSuccessHandler;
import com.sasindu.shoppingcart.helpers.ValidationHelper;
import com.sasindu.shoppingcart.models.Category;
import com.sasindu.shoppingcart.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * CategoryController class is responsible for handling all the API requests related to the category
 */
@RestController
@RequestMapping(ApplicationConstants.API_URL_PREFIX + "/category")
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService _categoryService;

    /**
     * saveCategory method is responsible for saving a category
     * this method calls the addCategory method of the CategoryService class internally
     *
     * @param request AddCategoryRequest object containing the category details
     * @return ApiResponse object containing the response details
     */
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> saveCategory(@RequestBody AddCategoryRequest request) {
        try {
            // validate the request
            ValidationHelper.validateModelBinding(request);

            CategoryResponse category = _categoryService.addCategory(request).toCategoryResponse();
            return GlobalSuccessHandler.handleSuccess("Category saved successfully", category, HttpStatus.CREATED.value(), null);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e);
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
            CategoryResponse category = _categoryService.getCategoryById(id).toCategoryResponse();
            return GlobalSuccessHandler.handleSuccess("Category fetched successfully", category, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e);
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
            CategoryResponse category = _categoryService.getCategoryByName(name).toCategoryResponse();
            return GlobalSuccessHandler.handleSuccess("Category fetched successfully", category, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e);
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
            List<CategoryResponse> categories = _categoryService.getAllCategories().stream().map(Category::toCategoryResponse).toList();
            return GlobalSuccessHandler.handleSuccess("Categories fetched successfully", categories, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e);
        }
    }


    /**
     * updateCategory method is responsible for updating a category
     * this method calls the updateCategory method of the CategoryService class internally
     *
     * @param request UpdateCategoryRequest object containing the updated category details
     * @param id      Long value of the category id
     * @return ApiResponse object containing the response details
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@RequestBody UpdateCategoryRequest request, @PathVariable Long id) {
        try {
            // validate the request
            ValidationHelper.validateModelBinding(request);

            CategoryResponse category = _categoryService.updateCategory(request, id).toCategoryResponse();
            return GlobalSuccessHandler.handleSuccess("Category updated successfully", category, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e);
        }
    }


    /**
     * deleteCategory method is responsible for deleting a category
     * this method calls the deleteCategoryById method of the CategoryService class internally
     *
     * @param id Long value of the category id
     * @return ApiResponse object containing the response details
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        try {
            _categoryService.deleteCategoryById(id);
            return GlobalSuccessHandler.handleSuccess("Category deleted successfully", null, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e);
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
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable String category) {
        try {
            List<ProductResponse> products = _categoryService.getAllProductsForCategory(category).stream().map(Product::toProductResponse).toList();
            return GlobalSuccessHandler.handleSuccess("All products for category", products, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e);
        }
    }
}


/*
 * ENDPOINTS
 * 1. create category - POST - http://localhost:9091/api/v1/category/create
 * 2. find category by id - GET - http://localhost:9091/api/v1/category/find-by-id/{id}
 * 3. find category by name - GET - http://localhost:9091/api/v1/category/find-by-name/{name}
 * 4. find all categories - GET - http://localhost:9091/api/v1/category/get-all
 * 5. update category - PUT - http://localhost:9091/api/v1/category/update/{id}
 * 6. delete category - DELETE - http://localhost:9091/api/v1/category/delete/{id}
 * 7. get products for category - GET - http://localhost:9091/api/v1/category/get-products/{category}
 */