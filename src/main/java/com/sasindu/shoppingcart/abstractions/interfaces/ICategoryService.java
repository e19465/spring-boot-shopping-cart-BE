package com.sasindu.shoppingcart.abstractions.interfaces;

import com.sasindu.shoppingcart.abstractions.dto.request.category.AddCategoryRequestDto;
import com.sasindu.shoppingcart.abstractions.dto.request.category.UpdateCategoryRequestDto;
import com.sasindu.shoppingcart.models.Category;
import com.sasindu.shoppingcart.models.Product;

import java.util.List;

/**
 * ICategoryService interface is responsible for defining the methods that should be implemented by the CategoryService class
 */
public interface ICategoryService {

    /**
     * addCategory method is responsible for adding a new category to the database
     *
     * @param request AddCategoryRequest object containing the category details
     * @return Category object containing the added category details
     */
    Category addCategory(AddCategoryRequestDto request);

    /**
     * getCategoryById method is responsible for fetching a category by its id
     *
     * @param id Long value of the category id
     * @return Category object containing the category details
     */
    Category getCategoryById(Long id);


    /**
     * getCategoryByName method is responsible for fetching a category by its name
     *
     * @param name String value of the category name
     * @return Category object containing the category details
     */
    Category getCategoryByName(String name);


    /**
     * getAllCategories method is responsible for fetching all the categories
     *
     * @return List of Category objects containing the category details
     */
    List<Category> getAllCategories();


    /**
     * updateCategory method is responsible for updating a category
     *
     * @param updateCategoryRequest UpdateCategoryRequest object containing the updated category details
     * @param id                    Long value of the category id
     * @return Category object containing the updated category details
     */
    Category updateCategory(UpdateCategoryRequestDto updateCategoryRequest, Long id);


    /**
     * deleteCategoryById method is responsible for deleting a category by its id
     *
     * @param id Long value of the category id
     */
    void deleteCategoryById(Long id);


    /**
     * getAllProductsForCategory method is responsible for fetching all the products for a given category
     *
     * @param categoryName String value of the category name
     * @return List of Product objects containing the product details
     */
    List<Product> getAllProductsForCategory(String categoryName);


    /**
     * saveCategory method is responsible for saving a category
     *
     * @param category Category object containing the category details
     */
    Category saveCategory(Category category);
}
