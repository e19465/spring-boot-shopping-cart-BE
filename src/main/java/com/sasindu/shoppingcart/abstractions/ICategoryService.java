package com.sasindu.shoppingcart.abstractions;

import com.sasindu.shoppingcart.dto.request.category.AddCategoryRequest;
import com.sasindu.shoppingcart.dto.request.category.UpdateCategoryRequest;
import com.sasindu.shoppingcart.dto.response.category.CategoryResponse;
import java.util.List;

/**
 * ICategoryService interface is responsible for defining the methods that should be implemented by the CategoryService class
 */
public interface ICategoryService {

    /**
     * addCategory method is responsible for adding a new category to the database
     * @param request AddCategoryRequest object containing the category details
     * @return CategoryResponse object containing the added category details
     */
    CategoryResponse addCategory(AddCategoryRequest request);

    /**
     * getCategoryById method is responsible for fetching a category by its id
     * @param id Long value of the category id
     * @return CategoryResponse object containing the category details
     */
    CategoryResponse getCategoryById(Long id);


    /**
     * getCategoryByName method is responsible for fetching a category by its name
     * @param name String value of the category name
     * @return CategoryResponse object containing the category details
     */
    CategoryResponse getCategoryByName(String name);


    /**
     * getAllCategories method is responsible for fetching all the categories
     * @return List of CategoryResponse objects containing the category details
     */
    List<CategoryResponse> getAllCategories();


    /**
     * updateCategory method is responsible for updating a category
     * @param updateCategoryRequest UpdateCategoryRequest object containing the updated category details
     * @param id Long value of the category id
     * @return CategoryResponse object containing the updated category details
     */
    CategoryResponse updateCategory(UpdateCategoryRequest updateCategoryRequest, Long id);


    /**
     * deleteCategoryById method is responsible for deleting a category by its id
     * @param id Long value of the category id
     */
    void deleteCategoryById(Long id);
}
