package com.sasindu.shoppingcart.dto.response.category;

import com.sasindu.shoppingcart.models.Category;
import com.sasindu.shoppingcart.models.Product;
import lombok.Data;
import java.util.List;

@Data
public class CategoryResponse {
    private Long id;
    private String name;
    List<Product> products;

    public Category toCategory() {
        Category category = new Category();
        category.setId(this.id);
        category.setName(this.name);
        category.setProducts(this.products);
        return category;
    }
}
