package com.cms.service;

import com.cms.controller.ImageController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageService {

    @Value("${server.host}")
    private String host;

    @Value("${image.upload.path}")
    private String imageUploadDir;

    public byte[] readFile(String filename) throws IOException {
        Path path = Paths.get(imageUploadDir, filename);
        if (path.toFile().exists()) {
            return Files.readAllBytes(path);
        }
        return null;
    }

    public String saveImage(MultipartFile file) throws IOException {
        Path fileNameAndPath = Paths.get(imageUploadDir, file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
        return getImageUrlWithHost(file.getOriginalFilename());
    }

    public String saveImage(String url) {
        try {
            String filename = url.substring(url.lastIndexOf("/") + 1);
            String ext = filename.substring(filename.lastIndexOf(".") + 1);
            BufferedImage bufferedImage = ImageIO.read(new URL(url));
            ImageIO.write(bufferedImage, ext, Paths.get(imageUploadDir, filename).toFile());
            return getImageUrlWithHost(filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getImageUrlWithHost(String filename) {
        String requestPath = ImageController.class.getAnnotation(RequestMapping.class).value()[0];
        return host + requestPath + "/" + filename;
    }
}
