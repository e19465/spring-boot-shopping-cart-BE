package com.sasindu.shoppingcart.dto.request.image;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateImageRequest {
    @NotNull(message = "Please select an image")
    private MultipartFile file;
}
