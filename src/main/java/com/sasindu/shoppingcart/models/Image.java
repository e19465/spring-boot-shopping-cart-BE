package com.sasindu.shoppingcart.models;

import com.sasindu.shoppingcart.abstractions.dto.response.image.ImageResponseDto;
import com.sasindu.shoppingcart.abstractions.dto.response.image.ImageResponseWithoutBlobDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String fileType;

    private String downloadUrl;

    @Lob
    private Blob image;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    /**
     * Convert the image to an image response
     *
     * @return ImageResponse
     */
    public ImageResponseDto toImageResponse() {
        ImageResponseDto response = new ImageResponseDto();
        response.setId(this.id);
        response.setFileName(this.fileName);
        response.setFileType(this.fileType);
        response.setDownloadUrl(this.downloadUrl);
        response.setImage(this.image);
        response.setProduct(this.product.toProductResponse());
        return response;
    }

    /**
     * Convert the image to an image response without blob
     *
     * @return ImageResponseWithoutBlob
     */
    public ImageResponseWithoutBlobDto toImageResponseWithoutBlob() {
        ImageResponseWithoutBlobDto response = new ImageResponseWithoutBlobDto();
        response.setId(this.id);
        response.setFileName(this.fileName);
        response.setFileType(this.fileType);
        response.setDownloadUrl(this.downloadUrl);
        response.setProduct(this.product.toProductResponse());
        return response;
    }
}
