package com.sasindu.shoppingcart.dto.response.product;

import com.sasindu.shoppingcart.models.Category;
import com.sasindu.shoppingcart.models.Image;
import com.sasindu.shoppingcart.models.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private String imageUrl;
    private List<Image> images;
    private String category;
}
