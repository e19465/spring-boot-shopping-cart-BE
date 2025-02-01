package com.sasindu.shoppingcart.repository;

import com.sasindu.shoppingcart.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CategoryRepository interface is responsible for handling the database operations related to the category
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * findByName method is responsible for fetching a category by its name
     * @param name String value of the category name
     * @return Category object containing the category details
     */
    Category findByName(String name);


    /**
     * existsByName method is responsible for checking if a category exists by its name
     * @param name String value of the category name
     * @return boolean value indicating if the category exists
     */
    boolean existsByName(String name);
}
