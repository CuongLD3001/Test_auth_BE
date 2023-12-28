package com.example.cuongld.service;

import com.example.cuongld.data.ImageRepository;
import com.example.cuongld.model.Image;
import com.example.cuongld.model.dto.ImageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public List<Image> getAllImages() {
        List<Image> images = imageRepository.findAll();
//        List<ImageDTO> imageDTOs = new ArrayList<>();
//        System.out.println("alo:" + images);
//        for (Image image : images) {
//            ImageDTO imageDTO = new ImageDTO();
//            imageDTO.setId(image.getId());
//            imageDTO.setImageName(image.getImageName());
//            imageDTO.setUrl(image.getUrl());
//
//            imageDTOs.add(imageDTO);
//        }

        return images;
    }

    @Override
    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }
}