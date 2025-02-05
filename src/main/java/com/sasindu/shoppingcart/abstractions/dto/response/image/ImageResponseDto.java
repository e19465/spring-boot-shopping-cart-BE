package com.sasindu.shoppingcart.abstractions.dto.response.image;

import com.sasindu.shoppingcart.abstractions.dto.response.product.ProductResponseDto;
import lombok.Data;

import java.sql.Blob;

@Data
public class ImageResponseDto {
    private Long id;
    private String fileName;
    private String fileType;
    private String downloadUrl;
    private Blob image;
    private ProductResponseDto product;
}
