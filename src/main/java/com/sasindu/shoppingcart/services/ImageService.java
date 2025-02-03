package com.sasindu.shoppingcart.services;

import com.sasindu.shoppingcart.abstractions.IImageService;
import com.sasindu.shoppingcart.constants.ApplicationConstants;
import com.sasindu.shoppingcart.exceptions.NotFoundException;
import com.sasindu.shoppingcart.models.Image;
import com.sasindu.shoppingcart.models.Product;
import com.sasindu.shoppingcart.repository.ImageRepository;
import com.sasindu.shoppingcart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.util.List;
import java.util.UUID;
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
     *
     * @param id Long value of the image id
     * @return Image object containing the image details
     */
    @Override
    public Image getImageById(Long id) {
        try {
            return _imageRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("No image found with id: " + id));
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch image: " + e.getMessage(), e);
        }
    }


    /**
     * deleteImageById method is responsible for deleting an image by its id
     *
     * @param id Long value of the image id
     */
    @Override
    public void deleteImageById(Long id) {
        try {
            Image image = _imageRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("No image found with id: " + id));
            _imageRepository.delete(image);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete image: " + e.getMessage(), e);
        }
    }


    /**
     * saveImage method is responsible for saving an image
     *
     * @param files     Files list of MultipartFile objects containing the image details
     * @param productId Long value of the product id
     */
    @Override
    public List<Image> saveImages(List<MultipartFile> files, Long productId) {
        try {
            // Check if the product exists
            Product product = _productRepository.findById(productId)
                    .orElseThrow(() -> new NotFoundException("No product found with id: " + productId));

            // Iterate over each file and save it
            return files.stream().map(file -> {
                try {
                    // Create a new image instance
                    Image image = new Image();
                    String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

                    image.setFileName(uniqueFileName);
                    image.setFileType(file.getContentType());
                    image.setImage(new SerialBlob(file.getBytes())); // Store image as Blob
                    image.setProduct(product);

                    // Save the image to the database
                    Image savedImage = _imageRepository.save(image);

                    // Set the download URL after saving the image
                    savedImage.setDownloadUrl(IMAGE_DOWNLOAD_URL_PREFIX + savedImage.getId());

                    // Save the image again to update with the correct download URL
                    _imageRepository.save(savedImage);
                    return savedImage;
                } catch (Exception e) {
                    throw new RuntimeException("Failed to save image: " + file.getOriginalFilename() + " due to " + e.getMessage(), e);
                }
            }).collect(Collectors.toList());
        } catch (NotFoundException e) {
            throw new NotFoundException("Product not found for id: " + productId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save images: " + e.getMessage(), e);
        }
    }


    /**
     * updateImage method is responsible for updating an image
     *
     * @param file    MultipartFile object containing the image details
     * @param imageId Long value of the image id
     * @return Image object containing the updated image details
     */
    @Override
    public Image updateImage(MultipartFile file, Long imageId) {
        try {

            // Check if the image exists
            Image image = _imageRepository.findById(imageId)
                    .orElseThrow(() -> new NotFoundException("No image found with id: " + imageId));

            // Update the image
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            _imageRepository.save(image);
            return image;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update image: " + e.getMessage(), e);
        }
    }
}
