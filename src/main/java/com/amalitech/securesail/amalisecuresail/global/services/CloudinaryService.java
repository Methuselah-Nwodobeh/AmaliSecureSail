package com.amalitech.securesail.amalisecuresail.global.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@NoArgsConstructor
@Service
public class CloudinaryService {
//    service class for uploading images and getting images from cloundinary

    private final Cloudinary cloudinary = new Cloudinary(System.getenv("CLOUDINARY_URL"));


    public String uploadImage(MultipartFile file) throws IOException {
        cloudinary.config.secure = true;
        return cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url").toString();
    }

    public String getUrl(String publicId) {
        cloudinary.config.secure = true;
        return cloudinary.url().transformation(new Transformation<>().width(100).height(100)).format("jpg").generate(publicId);
    }

    public void deleteImage(String publicId) throws IOException {
        cloudinary.config.secure = true;
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }
}
