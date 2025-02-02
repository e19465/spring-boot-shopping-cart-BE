package com.sasindu.shoppingcart.controllers;


import com.sasindu.shoppingcart.abstractions.ICategoryService;
import com.sasindu.shoppingcart.constants.ApplicationConstants;
import com.sasindu.shoppingcart.dto.request.category.AddCategoryRequest;
import com.sasindu.shoppingcart.dto.request.category.UpdateCategoryRequest;
import com.sasindu.shoppingcart.dto.response.category.CategoryResponse;
import com.sasindu.shoppingcart.helpers.ApiResponse;
import com.sasindu.shoppingcart.helpers.GlobalExceptionHandler;
import com.sasindu.shoppingcart.helpers.GlobalSuccessHandler;
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
    public ResponseEntity<ApiResponse> saveCategory(@RequestParam AddCategoryRequest request) {
        try {
            CategoryResponse category = _categoryService.addCategory(request);
            return GlobalSuccessHandler.handleSuccess("Category saved successfully", category, HttpStatus.CREATED.value(), null);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e, "Category save failed");
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
            CategoryResponse category = _categoryService.getCategoryById(id);
            return GlobalSuccessHandler.handleSuccess("Category fetched successfully", category, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e, "Category fetch failed");
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
            CategoryResponse category = _categoryService.getCategoryByName(name);
            return GlobalSuccessHandler.handleSuccess("Category fetched successfully", category, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e, "Category fetch failed");
        }
    }


    /**
     * getAllCategories method is responsible for fetching all the categories
     * this method calls the getAllCategories method of the CategoryService class internall
     *
     * @return ApiResponse object containing the response details
     */
    @GetMapping("/find-all")
    public ResponseEntity<ApiResponse> getAllCategories() {
        try {
            List<CategoryResponse> categories = _categoryService.getAllCategories();
            return GlobalSuccessHandler.handleSuccess("Categories fetched successfully", categories, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e, "Categories fetch failed");
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
    public ResponseEntity<ApiResponse> updateCategory(@RequestParam UpdateCategoryRequest request, @PathVariable Long id) {
        try {
            CategoryResponse category = _categoryService.updateCategory(request, id);
            return GlobalSuccessHandler.handleSuccess("Category updated successfully", category, HttpStatus.OK.value(), null);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e, "Category update failed");
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
            return GlobalExceptionHandler.handleException(e, "Category delete failed");
        }
    }
}


/*
 * ENDPOINTS
 * 1. create category - POST - http://localhost:9091/api/v1/category/create
 * 2. find category by id - GET - http://localhost:9091/api/v1/category/find-by-id/{id}
 * 3. find category by name - GET - http://localhost:9091/api/v1/category/find-by-name/{name}
 * 4. find all categories - GET - http://localhost:9091/api/v1/category/find-all
 * 5. update category - PUT - http://localhost:9091/api/v1/category/update/{id}
 * 6. delete category - DELETE - http://localhost:9091/api/v1/category/delete/{id}
 */