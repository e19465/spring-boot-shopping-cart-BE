package com.sasindu.shoppingcart.dto.request.category;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddCategoryRequest {
    @NotNull(message = "Category name is required")
    private String name;
}
