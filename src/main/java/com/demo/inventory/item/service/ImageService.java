package com.demo.inventory.item.service;

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

    public Image addImage(Long imageId, MultipartFile file, String authToken) throws IOException {
        Long userId = itemRepository.findByItemId(imageId).getUserId();
        authChecker.checkUserAttachingTheirInfo(userId, authToken);
        Image image = new Image();
        image.setImageId(imageId);
        image.setImageBytes(file.getBytes());
        return imageRepository.save(image);
    }

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }
}
