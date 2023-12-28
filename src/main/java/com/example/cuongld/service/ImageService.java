package com.example.cuongld.service;

import com.example.cuongld.model.Image;

import java.util.List;

public interface ImageService {
    List<Image> getAllImages();
    Image saveImage(Image image);
    void deleteImage(Long id);
}