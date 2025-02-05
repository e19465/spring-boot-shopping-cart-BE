package com.sasindu.shoppingcart.abstractions.dto.request.product;

import com.sasindu.shoppingcart.models.Category;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class AddProductRequest {
    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Brand is required")
    private String brand;

    @NotNull(message = "Price is required")
    private BigDecimal price;

    @NotNull(message = "Inventory is required")
    private int inventory;

    @NotNull(message = "Description is required")
    private String description;

    @NotNull(message = "Category is required")
    private Category category;
}
