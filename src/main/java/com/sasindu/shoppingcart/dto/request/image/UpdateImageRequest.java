package com.sasindu.shoppingcart.dto.request.image;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateImageRequest {
    private MultipartFile file;
}
