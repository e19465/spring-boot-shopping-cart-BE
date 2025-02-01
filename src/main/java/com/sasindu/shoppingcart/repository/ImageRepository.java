package com.sasindu.shoppingcart.repository;

import com.sasindu.shoppingcart.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Image Repository is responsible for handling the database operations related to the image
 */
public interface ImageRepository extends JpaRepository<Image, Long> {
}
