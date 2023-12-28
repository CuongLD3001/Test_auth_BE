package com.example.cuongld.controller;

import com.example.cuongld.model.Image;
import com.example.cuongld.model.dto.ImageDTO;
import com.example.cuongld.model.dto.ImageResponse;
import com.example.cuongld.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping
    public ImageResponse getAllImages() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getAuthorities());
        List<Image> images = imageService.getAllImages();
        return new ImageResponse(images);
    }

    @PostMapping
    public ResponseEntity<Image> addImage(@RequestBody Image image) {
        Image savedImage = imageService.saveImage(image);
        return new ResponseEntity<>(savedImage, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
