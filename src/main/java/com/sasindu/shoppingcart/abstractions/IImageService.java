package com.sasindu.shoppingcart.abstractions;

import com.sasindu.shoppingcart.dto.request.image.AddImageRequest;
import com.sasindu.shoppingcart.dto.request.image.UpdateImageRequest;
import com.sasindu.shoppingcart.dto.response.image.ImageResponse;
import java.util.List;


/**
 * IImageService interface is responsible for defining the methods that should be implemented by the ImageService class
 */
public interface IImageService {

    /**
     * getImageById method is responsible for fetching an image by its id
     * @param id Long value of the image id
     * @return ImageResponse object containing the image details
     */
    ImageResponse getImageById(Long id);



    /**
     * deleteImageById method is responsible for deleting an image by its id
     * @param id Long value of the image id
     * This method does not return anything
     */
    void deleteImageById(Long id);



    /**
     * saveImage method is responsible for saving an image
     * @param request AddImageRequest object containing the image details
     * @param productId Long value of the product id
     * @return List of ImageResponse objects containing the image details
     */
    List<ImageResponse> saveImages(AddImageRequest request, Long productId);



    /**
     * updateImage method is responsible for updating an image
     * @param request UpdateImageRequest object containing the updated image details
     * @param imageId Long value of the image id
     * @return ImageResponse object containing the updated image details
     */
    ImageResponse updateImage(UpdateImageRequest request, Long imageId);
}
