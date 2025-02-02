package com.sasindu.shoppingcart.abstractions;

import com.sasindu.shoppingcart.dto.response.image.ImageResponse;
import com.sasindu.shoppingcart.dto.response.image.ImageResponseWithoutBlob;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * IImageService interface is responsible for defining the methods that should be implemented by the ImageService class
 */
public interface IImageService {

    /**
     * getImageById method is responsible for fetching an image by its id
     *
     * @param id Long value of the image id
     * @return ImageResponse object containing the image details
     */
    ImageResponse getImageById(Long id);


    /**
     * deleteImageById method is responsible for deleting an image by its id
     *
     * @param id Long value of the image id
     *           This method does not return anything
     */
    void deleteImageById(Long id);


    /**
     * saveImage method is responsible for saving an image
     *
     * @param files     Files list of MultipartFile objects containing the image details
     * @param productId Long value of the product id
     */
    List<ImageResponseWithoutBlob> saveImages(List<MultipartFile> files, Long productId);


    /**
     * updateImage method is responsible for updating an image
     *
     * @param file    MultipartFile object containing the image details
     * @param imageId Long value of the image id
     * @return ImageResponse object containing the updated image details
     */
    ImageResponse updateImage(MultipartFile file, Long imageId);
}
