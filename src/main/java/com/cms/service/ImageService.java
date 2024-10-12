package com.cms.service;

import com.cms.controller.ImageController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageService {

    private static final Logger log = LoggerFactory.getLogger(ImageService.class);

    @Value("${server.host}")
    private String host;

    @Value("${image.upload.dir}")
    private String imageUploadDir;

    public byte[] readFile(String filename) throws IOException {
        Path path = Paths.get(imageUploadDir, filename);
        if (path.toFile().exists()) {
            return Files.readAllBytes(path);
        }
        return new byte[0];
    }

    public String saveImage(MultipartFile file) throws IOException {
        Files.createDirectories(Paths.get(imageUploadDir));
        String name = file.getOriginalFilename().replaceAll(" ", "-");
        Path fileNameAndPath = Paths.get(imageUploadDir, name);
        log.info("Saving image '{}' with name: '{}'", file.getOriginalFilename(), name);
        Files.write(fileNameAndPath, file.getBytes());
        return getImageUrlWithHost(name);
    }

    private String getImageUrlWithHost(String filename) {
        String requestPath = ImageController.class.getAnnotation(RequestMapping.class).value()[0];
        return host + requestPath + "/" + filename;
    }
}
