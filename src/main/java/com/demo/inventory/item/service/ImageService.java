package com.demo.inventory.item.service;

import com.demo.inventory.exception.ItemException;
import com.demo.inventory.exception.RequestedObjectNotFoundException;
import com.demo.inventory.item.dto.ImageDto;
import com.demo.inventory.item.mapper.ImageMapper;
import com.demo.inventory.item.model.Image;
import com.demo.inventory.item.model.Item;
import com.demo.inventory.item.repository.ImageRepository;
import com.demo.inventory.item.repository.ItemRepository;
import com.demo.inventory.item.utils.ItemUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ImageService {

    private final ImageRepository imageRepository;
    private final ItemRepository itemRepository;
    private final ImageMapper mapper;
    private final ItemUtils itemUtils;

    public ImageDto addImage(Long imageId, MultipartFile file, Long userId) throws IOException {
        Optional<Item> itemOptional = itemRepository.findByItemIdAndUserId(imageId, userId);

        if (itemOptional.isEmpty()) {
            throw new RequestedObjectNotFoundException(
                    String.format("Item with id [%d] and user id [%d] does not exist", imageId, userId));
        }

        validateImage(file, userId);

        Image image = Image.builder().imageId(imageId).imageBytes(file.getBytes()).build();

        return mapper.fromImage(imageRepository.save(image));
    }

    private void validateImage(MultipartFile file, Long userId) {
        if (!itemUtils.getAllowedFileTypes().contains(file.getContentType())) {
            log.warn(String.format(
                    "User with id [%d] attempted to add file with restricted file type!", userId));
            throw new ItemException("A file must be a image!");
        }
        if (file.getSize() > itemUtils.getMaxFileSize()) {
            log.warn(String.format(
                    "User with id [%d] attempted to add file with file size larger than allowed!", userId));
            throw new ItemException("A file cannot be over 1 Mb");
        }
    }
}
