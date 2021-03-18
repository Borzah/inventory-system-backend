package com.demo.inventory.item.service;

import com.demo.inventory.item.model.Image;
import com.demo.inventory.item.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public Image addImage(Long imageId, MultipartFile file) throws IOException {
        Image image = new Image();
        image.setImageId(imageId);
        image.setImageBytes(file.getBytes());
        return imageRepository.save(image);
    }

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }
}
