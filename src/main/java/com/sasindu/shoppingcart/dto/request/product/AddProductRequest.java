package com.sasindu.shoppingcart.dto.request.product;

import com.sasindu.shoppingcart.models.Category;
import lombok.Data;
import java.math.BigDecimal;


@Data
public class AddProductRequest {
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
