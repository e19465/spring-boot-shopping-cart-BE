package com.sasindu.shoppingcart.abstractions.dto.request.category;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddCategoryRequestDto {
    @NotNull(message = "Category name is required")
    private String name;
}
