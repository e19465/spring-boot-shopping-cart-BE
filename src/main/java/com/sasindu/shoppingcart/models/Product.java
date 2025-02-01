package com.sasindu.shoppingcart.models;

import com.sasindu.shoppingcart.dto.response.product.ProductResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String brand;

    private BigDecimal price;

    private int inventory;

    private String description;

    // One product can have multiple images
    // when product is deleted, all images related to that product should be deleted
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // Add the toProductResponse() method here
    public ProductResponse toProductResponse() {
        ProductResponse response = new ProductResponse();
        response.setId(this.id);
        response.setName(this.name);
        response.setBrand(this.brand);
        response.setPrice(this.price);
        response.setInventory(this.inventory);
        response.setDescription(this.description);
        response.setCategory(this.category);
        response.setImages(this.images);  // You can also map this to URLs if needed
        return response;
    }

    public Product(
            String name,
            String brand,
            BigDecimal price,
            int inventory,
            String description,
            Category category)
    {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.inventory = inventory;
        this.description = description;
        this.category = category;
    }
}
