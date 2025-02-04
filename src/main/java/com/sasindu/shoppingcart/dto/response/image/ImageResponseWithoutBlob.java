package com.sasindu.shoppingcart.dto.response.image;

import com.sasindu.shoppingcart.dto.response.product.ProductResponse;
import lombok.Data;

@Data
public class ImageResponseWithoutBlob {
    private Long id;
    private String fileName;
    private String fileType;
    private String downloadUrl;
    private ProductResponse product;
}
