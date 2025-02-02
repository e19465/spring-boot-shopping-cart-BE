package com.sasindu.shoppingcart.services;

import com.sasindu.shoppingcart.abstractions.IImageService;
import com.sasindu.shoppingcart.constants.ApplicationConstants;
import com.sasindu.shoppingcart.dto.request.image.AddImageRequest;
import com.sasindu.shoppingcart.dto.request.image.UpdateImageRequest;
import com.sasindu.shoppingcart.dto.response.image.ImageResponse;
import com.sasindu.shoppingcart.exceptions.NotFoundException;
import com.sasindu.shoppingcart.models.Image;
import com.sasindu.shoppingcart.models.Product;
import com.sasindu.shoppingcart.repository.ImageRepository;
import com.sasindu.shoppingcart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.sql.rowset.serial.SerialBlob;
import java.util.List;
import java.util.stream.Collectors;



/**
 * ImageService class is responsible for handling the business logic related to the image
 */
@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {
    private final ImageRepository _imageRepository;
    private final ProductRepository _productRepository;
    private static final String IMAGE_DOWNLOAD_URL_PREFIX = ApplicationConstants.IMAGE_DOWNLOAD_URL_PREFIX;

    /**
     * getImageById method is responsible for fetching an image by its id
     * @param id Long value of the image id
     * @return ImageResponse object containing the image details
     */
    @Override
    public ImageResponse getImageById(Long id) {
        Image image =  _imageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No image found with id: " + id));
        return image.toImageResponse();
    }



    /**
     * deleteImageById method is responsible for deleting an image by its id
     * @param id Long value of the image id
     */
    @Override
    public void deleteImageById(Long id) {
        _imageRepository.findById(id)
                .ifPresentOrElse(_imageRepository::delete, () -> {
                    throw new NotFoundException("No image found with id: " + id);
                });
    }



    /**
     * saveImage method is responsible for saving an image
     * @param request AddImageRequest object containing the image details
     * @param productId Long value of the product id
     * @return List of ImageResponse objects containing the image details
     */
    @Override
    public List<ImageResponse> saveImages(AddImageRequest request, Long productId) {
        Product product = _productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("No product found with id: " + productId));

        return request.getFiles().stream().map(file -> {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                Image savedImage = _imageRepository.save(image);
                savedImage.setDownloadUrl(IMAGE_DOWNLOAD_URL_PREFIX + savedImage.getId());

                _imageRepository.save(savedImage); // Update with the correct download URL

                return savedImage.toImageResponse();
            } catch (Exception e) {
                throw new RuntimeException("Failed to save image: " + e.getMessage(), e);
            }
        }).collect(Collectors.toList());
    }



    /**
     * updateImage method is responsible for updating an image
     * @param request UpdateImageRequest object containing the updated image details
     * @param imageId Long value of the image id
     * @return ImageResponse object containing the updated image details
     */
    @Override
    public ImageResponse updateImage(UpdateImageRequest request, Long imageId) {
        try{
        Image image = _imageRepository.findById(imageId)
                .orElseThrow(() -> new NotFoundException("No image found with id: " + imageId));
            image.setFileName(request.getFile().getOriginalFilename());
            image.setFileType(request.getFile().getContentType());
            image.setImage(new SerialBlob(request.getFile().getBytes()));
            _imageRepository.save(image);
            return image.toImageResponse();
        }
        catch (NotFoundException e) {
            throw e;
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to update image: " + e.getMessage(), e);
        }
    }
}
