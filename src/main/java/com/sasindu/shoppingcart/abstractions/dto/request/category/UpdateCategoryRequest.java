package com.sasindu.shoppingcart.abstractions.dto.request.category;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCategoryRequest {
    @NotNull(message = "Category name is required")
    private String name;
}
