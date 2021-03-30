package com.demo.inventory.item.service;

import com.demo.inventory.exception.ItemException;
import com.demo.inventory.item.dto.ImageDto;
import com.demo.inventory.item.model.Image;
import com.demo.inventory.item.repository.ImageRepository;
import com.demo.inventory.item.repository.ItemRepository;
import com.demo.inventory.security.AuthChecker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final ItemRepository itemRepository;
    private final AuthChecker authChecker;

    public ImageDto addImage(Long imageId, MultipartFile file, String authToken) throws IOException {
        Long userId = itemRepository.findByItemId(imageId).getUserId();
        authChecker.checkUserAttachingTheirInfo(userId, authToken);
        validateImage(file);
        Image image = Image.builder().imageId(imageId).imageBytes(file.getBytes()).build();
        return convertImage(imageRepository.save(image));
    }

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    private void validateImage(MultipartFile file) {
        List<String> allowedTypes = List.of("image/jpeg", "image/jpg", "image/png");
        if (!allowedTypes.contains(file.getContentType())) {
            throw new ItemException("A file must be a image!");
        }
        if (file.getSize() > 1000000L) {
            throw new ItemException("A file cannot be over 1 Mb");
        }
    }

    private ImageDto convertImage(Image image) {
        return ImageDto.builder().imageId(image.getImageId()).imageBytes(image.getImageBytes()).build();
    }
}
