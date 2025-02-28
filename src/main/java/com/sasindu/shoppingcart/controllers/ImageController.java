package com.sasindu.shoppingcart.controllers;

import com.sasindu.shoppingcart.abstractions.dto.response.image.ImageResponseDto;
import com.sasindu.shoppingcart.abstractions.dto.response.image.ImageResponseWithoutBlobDto;
import com.sasindu.shoppingcart.abstractions.interfaces.IImageService;
import com.sasindu.shoppingcart.helpers.ApiResponse;
import com.sasindu.shoppingcart.helpers.ErrorResponseHandler;
import com.sasindu.shoppingcart.helpers.SuccessResponseHandler;
import com.sasindu.shoppingcart.models.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * ImageController class is responsible for handling all the API requests related to the image
 */
@RestController
@RequestMapping("${api.prefix}" + "/images")
@RequiredArgsConstructor
public class ImageController {
    private final IImageService _imageService;

    /**
     * saveImages method is responsible for saving images
     * this method calls the saveImages method of the ImageService class internally
     * Only users with the role ROLE_ADMIN can access this endpoint
     *
     * @param files     MultipartFile list of objects containing the image details
     * @param productId Long value of the product id
     * @return ApiResponse object containing the response details
     */
    @PostMapping("/upload/{productId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files, @PathVariable Long productId) {
        try {
            List<ImageResponseWithoutBlobDto> images = _imageService.saveImages(files, productId).stream().map(Image::toImageResponseWithoutBlob).toList();
            return SuccessResponseHandler.handleSuccess("Upload successful", images, HttpStatus.CREATED.value(), null);
        } catch (Exception e) {
            return ErrorResponseHandler.handleException(e);
        }
    }


    /**
     * downloadImage method is responsible for downloading an image by its id
     * this method calls the getImageById method of the ImageService class internally
     * Only users with the role ROLE_ADMIN can access this endpoint
     *
     * @param imageId Long value of the image id
     * @return Resource object containing the image details
     */
    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<?> downloadImage(@PathVariable Long imageId) {
        try {
            ImageResponseDto image = _imageService.getImageById(imageId).toImageResponse();
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
            return ErrorResponseHandler.handleException(e);
        }
    }


    /**
     * updateImage method is responsible for updating an image
     * this method calls the updateImage method of the ImageService class internally
     * Only users with the role ROLE_ADMIN can access this endpoint
     *
     * @param file    MultipartFile object containing the image details
     * @param imageId Long value of the image id
     * @return ApiResponse object containing the response details
     */
    @PutMapping("image/update/{imageId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> updateImage(@RequestParam MultipartFile file, @PathVariable Long imageId) {
        try {
            ImageResponseWithoutBlobDto response = _imageService.updateImage(file, imageId).toImageResponseWithoutBlob();
            return SuccessResponseHandler.handleSuccess("Update successful", response, HttpStatus.OK.value(), null
            );
        } catch (Exception e) {
            return ErrorResponseHandler.handleException(e);
        }
    }


    /**
     * deleteImage method is responsible for deleting an image by its id
     * this method calls the deleteImageById method of the ImageService class internally
     * Only users with the role ROLE_ADMIN can access this endpoint
     *
     * @param imageId Long value of the image id
     * @return ApiResponse object containing the response details
     */
    @DeleteMapping("image/delete/{imageId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId) {
        try {
            _imageService.deleteImageById(imageId);
            return SuccessResponseHandler.handleSuccess("Delete successful", null, 200, null);
        } catch (Exception e) {
            return ErrorResponseHandler.handleException(e);
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