package com.sasindu.shoppingcart.dto.response.image;

import com.sasindu.shoppingcart.models.Product;
import lombok.Data;

import java.sql.Blob;

@Data
public class ImageResponse {
    private Long id;
    private String fileName;
    private String fileType;
    private String downloadUrl;
    private Blob image;
    private Product product;
}
