package com.cms.controller;

import com.cms.dto.ImageDto;
import com.cms.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping(value = "/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) throws IOException {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageService.readFile(filename));
    }

    @PostMapping
    public ImageDto upload(@RequestParam("file") MultipartFile file) throws IOException {
        String location = imageService.saveImage(file);
        return new ImageDto(location);
    }
}
