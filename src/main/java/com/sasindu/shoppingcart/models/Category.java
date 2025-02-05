package com.sasindu.shoppingcart.models;

import com.sasindu.shoppingcart.abstractions.dto.response.category.CategoryResponseDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
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

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = Collections.emptyList();

    public Category(String name) {
        this.name = name;
    }

    // Add the toCategoryResponse() method here
    public CategoryResponseDto toCategoryResponse() {
        CategoryResponseDto response = new CategoryResponseDto();
        response.setId(this.id);
        response.setName(this.name);
        return response;
    }
}
