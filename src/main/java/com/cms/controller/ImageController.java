package com.cms.controller;

import com.cms.dto.ImageDto;
import org.springframework.beans.factory.annotation.Value;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Value("${server.host}")
    private String host;

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/images";

    @GetMapping(value = "/{filename}", produces = {MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) throws IOException {
        Optional<Path> first = Files.list(Paths.get(UPLOAD_DIRECTORY))
                .filter(path -> path.toFile().isFile())
                .filter(path -> path.toFile().getName().equalsIgnoreCase(filename))
                .findFirst();
        byte[] bytes = first.map(path -> {
            try {
                return Files.readAllBytes(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).orElse(null);
        return ResponseEntity.ok().body(bytes);
    }

    @PostMapping
    public ImageDto upload(@RequestParam("file") MultipartFile file) throws IOException {
        Files.createDirectories(Paths.get(UPLOAD_DIRECTORY));
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
        ImageDto imageDto = new ImageDto();
        imageDto.setLocation(host + "/images/" + file.getOriginalFilename());
        return imageDto;
    }
}
