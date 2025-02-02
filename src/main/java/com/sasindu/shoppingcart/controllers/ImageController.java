package com.sasindu.shoppingcart.controllers;

import com.sasindu.shoppingcart.abstractions.IImageService;
import com.sasindu.shoppingcart.constants.ApplicationConstants;
import com.sasindu.shoppingcart.dto.response.image.ImageResponse;
import com.sasindu.shoppingcart.dto.response.image.ImageResponseWithoutBlob;
import com.sasindu.shoppingcart.helpers.ApiResponse;
import com.sasindu.shoppingcart.helpers.GlobalExceptionHandler;
import com.sasindu.shoppingcart.helpers.GlobalSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * ImageController class is responsible for handling all the API requests related to the image
 */
@RestController
@RequestMapping(ApplicationConstants.API_URL_PREFIX + "/images")
@RequiredArgsConstructor
public class ImageController {
    private final IImageService _imageService;
    private final GlobalExceptionHandler globalExceptionHandler;

    /**
     * saveImages method is responsible for saving images
     * this method calls the saveImages method of the ImageService class internally
     *
     * @param files     MultipartFile list of objects containing the image details
     * @param productId Long value of the product id
     * @return ApiResponse object containing the response details
     */
    @PostMapping("/upload/{productId}")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files, @PathVariable Long productId) {
        try {
            List<ImageResponseWithoutBlob> images = _imageService.saveImages(files, productId);
            return GlobalSuccessHandler.handleSuccess("Upload successful", images, HttpStatus.CREATED.value(), null);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e);
        }
    }


    /**
     * downloadImage method is responsible for downloading an image by its id
     * this method calls the getImageById method of the ImageService class internally
     *
     * @param imageId Long value of the image id
     * @return Resource object containing the image details
     */
    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<?> downloadImage(@PathVariable Long imageId) {
        try {
            ImageResponse image = _imageService.getImageById(imageId);
            ByteArrayResource resource = new ByteArrayResource(
                    image.getImage().getBytes(1, (int) image.getImage().length())
            );
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(image.getFileType()));
            headers.setContentDispositionFormData("attachment", image.getFileName());
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e);
        }
    }


    /**
     * updateImage method is responsible for updating an image
     * this method calls the updateImage method of the ImageService class internally
     *
     * @param file    MultipartFile object containing the image details
     * @param imageId Long value of the image id
     * @return ApiResponse object containing the response details
     */
    @PutMapping("image/update/{imageId}")
    public ResponseEntity<ApiResponse> updateImage(@RequestParam MultipartFile file, @PathVariable Long imageId) {
        try {
            _imageService.updateImage(file, imageId);
            return GlobalSuccessHandler.handleSuccess("Update successful", null, HttpStatus.OK.value(), null
            );
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e);
        }
    }

    @DeleteMapping("image/delete/{imageId}")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId) {
        try {
            _imageService.deleteImageById(imageId);
            return GlobalSuccessHandler.handleSuccess("Delete successful", null, 200, null);
        } catch (Exception e) {
            return GlobalExceptionHandler.handleException(e);
        }
    }
}


/*
 * ENDPOINTS
 * 1. upload image - POST - http://localhost:9091/api/v1/images/upload/{productId}
 * 2. download image - GET - http://localhost:9091/api/v1/images/image/download/{imageId}
 * 3. update image - PUT - http://localhost:9091/api/v1/images/image/update/{imageId}
 * 4. delete image - DELETE - http://localhost:9091/api/v1/images/image/delete/{imageId}
 */