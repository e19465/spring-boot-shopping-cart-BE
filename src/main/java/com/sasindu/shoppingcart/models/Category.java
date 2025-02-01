package com.sasindu.shoppingcart.models;

import com.sasindu.shoppingcart.dto.response.category.CategoryResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany (mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;

    public Category(String name) {
        this.name = name;
    }

    // Add the toCategoryResponse() method here
    public CategoryResponse toCategoryResponse() {
        CategoryResponse response = new CategoryResponse();
        response.setId(this.id);
        response.setName(this.name);
        response.setProducts(this.products);
        return response;
    }
}
