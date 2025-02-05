package com.sasindu.shoppingcart.abstractions.dto.response.image;

import com.sasindu.shoppingcart.abstractions.dto.response.product.ProductResponseDto;
import lombok.Data;

@Data
public class ImageResponseWithoutBlobDto {
    private Long id;
    private String fileName;
    private String fileType;
    private String downloadUrl;
    private ProductResponseDto product;
}
