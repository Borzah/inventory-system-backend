package com.demo.inventory.item.service;

import com.demo.inventory.exception.ItemException;
import com.demo.inventory.exception.RequestedObjectNotFoundException;
import com.demo.inventory.item.dto.ImageDto;
import com.demo.inventory.item.mapper.ImageMapper;
import com.demo.inventory.item.model.Image;
import com.demo.inventory.item.model.Item;
import com.demo.inventory.item.repository.ImageRepository;
import com.demo.inventory.item.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final ItemRepository itemRepository;
    private final ImageMapper mapper;

    public ImageDto addImage(Long imageId, MultipartFile file, Long userId) throws IOException {
        Optional<Item> itemOptional = itemRepository.findByItemIdAndUserId(imageId, userId);

        if (itemOptional.isEmpty()) {
            throw new RequestedObjectNotFoundException(
                    String.format("Item with id [%d] and user id [%d] does not exist", imageId, userId));
        }

        validateImage(file);

        Image image = Image.builder().imageId(imageId).imageBytes(file.getBytes()).build();

        return mapper.fromImage(imageRepository.save(image));
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
}
