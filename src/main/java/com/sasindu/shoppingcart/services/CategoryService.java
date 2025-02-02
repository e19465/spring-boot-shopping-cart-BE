package com.sasindu.shoppingcart.services;

import com.sasindu.shoppingcart.abstractions.ICategoryService;
import com.sasindu.shoppingcart.dto.request.category.AddCategoryRequest;
import com.sasindu.shoppingcart.dto.request.category.UpdateCategoryRequest;
import com.sasindu.shoppingcart.dto.response.category.CategoryResponse;
import com.sasindu.shoppingcart.exceptions.BadRequestException;
import com.sasindu.shoppingcart.exceptions.ConflictException;
import com.sasindu.shoppingcart.exceptions.NotFoundException;
import com.sasindu.shoppingcart.helpers.ValidationHelper;
import com.sasindu.shoppingcart.models.Category;
import com.sasindu.shoppingcart.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * CategoryService class is responsible for handling all the business logic related to the category
 * It implements the ICategoryService interface
 */
@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository _categoryRepository;


    /**
     * addCategory method is responsible for adding a new category to the database
     *
     * @param request AddCategoryRequest object containing the category details
     * @return CategoryResponse object containing the added category details
     * @throws ConflictException if the category already exists
     */
    @Override
    public CategoryResponse addCategory(AddCategoryRequest request) {
        try {
            // Validate the request body
            ValidationHelper.validateModelBinding(request);

            // Check if the category already exists
            return Optional.of(request)
                    .filter(c -> !_categoryRepository.existsByName(c.getName()))
                    .map(c -> _categoryRepository.save(new Category(c.getName())).toCategoryResponse())
                    .orElseThrow(() -> new ConflictException("Category already exists"));
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save category: " + e.getMessage(), e);
        }
    }


    /**
     * getCategoryById method is responsible for fetching a category by its id
     *
     * @param id Long value of the category id
     * @return CategoryResponse object containing the category details
     * @throws NotFoundException if the category is not found
     */
    @Override
    public CategoryResponse getCategoryById(Long id) {
        try {
            Category category = _categoryRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Category not found"));
            return category.toCategoryResponse();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch category: " + e.getMessage(), e);
        }
    }


    /**
     * getCategoryByName method is responsible for fetching a category by its name
     *
     * @param name String value of the category name
     * @return CategoryResponse object containing the category details
     * @throws NotFoundException if the category is not found
     */
    @Override
    public CategoryResponse getCategoryByName(String name) {
        try {
            Category category = _categoryRepository.findByName(name);
            if (category == null) {
                throw new NotFoundException("Category not found");
            }
            return category.toCategoryResponse();
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch category: " + e.getMessage(), e);
        }
    }


    /**
     * getAllCategories method is responsible for fetching all the categories from the database
     *
     * @return List of CategoryResponse objects containing the category details
     */
    @Override
    public List<CategoryResponse> getAllCategories() {
        try {
            return _categoryRepository.findAll().stream()
                    .map(Category::toCategoryResponse)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch categories: " + e.getMessage(), e);
        }
    }


    /**
     * updateCategory method is responsible for updating a category
     *
     * @param request UpdateCategoryRequest object containing the updated category details
     * @param id      Long value of the category id
     * @return CategoryResponse object containing the updated category details
     * @throws NotFoundException if the category is not found
     */
    @Override
    public CategoryResponse updateCategory(UpdateCategoryRequest request, Long id) {
        try {
            return _categoryRepository.findById(id)
                    .map(category -> {
                        category.setName(request.getName());
                        return _categoryRepository.save(category).toCategoryResponse();
                    })
                    .orElseThrow(() -> new NotFoundException("Category not found"));
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update category: " + e.getMessage(), e);
        }
    }


    /**
     * deleteCategoryById method is responsible for deleting a category by its id
     *
     * @param id Long value of the category id
     * @throws NotFoundException if the category is not found
     */
    @Override
    public void deleteCategoryById(Long id) {
        try {
            _categoryRepository.findById(id)
                    .ifPresentOrElse(_categoryRepository::delete, () -> {
                        throw new NotFoundException("Category not found");
                    });
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete category: " + e.getMessage(), e);
        }
    }
}
