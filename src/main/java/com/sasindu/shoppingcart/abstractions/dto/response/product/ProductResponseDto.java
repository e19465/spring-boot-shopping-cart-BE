package com.sasindu.shoppingcart.abstractions.dto.response.product;

import com.sasindu.shoppingcart.abstractions.dto.response.category.CategoryResponseDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductResponseDto {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private String imageUrl;
    private List<String> images;
    private CategoryResponseDto category;
}
