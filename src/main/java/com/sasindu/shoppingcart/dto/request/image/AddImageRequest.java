package com.sasindu.shoppingcart.dto.request.image;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Data
public class AddImageRequest {
    private List<MultipartFile> files;
}
