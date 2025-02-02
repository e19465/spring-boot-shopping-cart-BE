package com.sasindu.shoppingcart.models;

import com.sasindu.shoppingcart.dto.response.image.ImageResponse;
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


    // Add the toImageResponse() method here
    public ImageResponse toImageResponse() {
        ImageResponse response = new ImageResponse();
        response.setId(this.id);
        response.setFileName(this.fileName);
        response.setFileType(this.fileType);
        response.setDownloadUrl(this.downloadUrl);
        response.setImage(this.image);
        response.setProduct(this.product);
        return response;
    }
}
