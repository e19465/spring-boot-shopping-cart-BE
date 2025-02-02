package com.sasindu.shoppingcart.dto.request.image;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class AddImageRequest {
    @NotNull(message = "Please select at least one image")
    private List<MultipartFile> files;
}
