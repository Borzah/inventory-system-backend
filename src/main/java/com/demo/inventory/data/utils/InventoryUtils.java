package com.demo.inventory.data.utils;

import com.demo.inventory.data.dto.ItemNodeResponse;
import com.demo.inventory.data.dto.ItemResponse;
import com.demo.inventory.data.mapper.ItemResponseMapper;
import com.demo.inventory.exception.RequestedObjectNotFoundException;
import com.demo.inventory.item.model.Category;
import com.demo.inventory.item.model.Folder;
import com.demo.inventory.item.model.Image;
import com.demo.inventory.item.model.Item;
import com.demo.inventory.item.repository.CategoryRepository;
import com.demo.inventory.item.repository.FolderRepository;
import com.demo.inventory.item.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class InventoryUtils {

    private final ImageRepository imageRepository;
    private final CategoryRepository categoryRepository;
    private final FolderRepository folderRepository;
    private final ItemResponseMapper mapper;

    // Name of the first section in inventory
    public static final String ROOT_NAME = "My-Items";

    public ItemNodeResponse createItemNodeResponse(Item item) {
        return new ItemNodeResponse(item.getItemId(), item.getItemName());
    }

    public ItemResponse createItemResponse(Item item) {
        byte[] imageBytes = new byte[]{};
        String categoryName = null;
        String folderName = null;

        Optional<Image> imageOptional = imageRepository.findById(item.getItemId());
        if (imageOptional.isPresent()) {
            imageBytes = imageOptional.get().getImageBytes();
        }

        if (Optional.ofNullable(item.getCategoryId()).isPresent()) {
            Category category = categoryRepository.findById(item.getCategoryId())
                    .orElseThrow(() -> new RequestedObjectNotFoundException(
                            String.format(
                                    "Category with id [%d] does not exist",
                                    item.getCategoryId())
                    ));
            categoryName = category.getCategoryName();
        }

        if (Optional.ofNullable(item.getFolderId()).isPresent()) {
            Folder folder = folderRepository.findById(item.getFolderId())
                    .orElseThrow(() -> new RequestedObjectNotFoundException(
                            String.format(
                                    "Folder with id [%d] does not exist",
                                    item.getFolderId())
                    ));
            folderName = folder.getFolderName();
        }

        return mapper.itemToItemResponse(item, folderName, categoryName, imageBytes);
    }

    public String getFolderPathName(List<Folder> searchableSpace, Folder currentFolder) {
        List<Folder> result = new ArrayList<>();
        List<String> resultString = new ArrayList<>();
        result.add(currentFolder);
        resultString.add(currentFolder.getFolderName());

        int listLength = searchableSpace.size();
        for (int i = 0; i < listLength; i++) {
            for (Folder f: searchableSpace) {
                Long comparable = result.get(result.size()-1).getParentId();
                if (Optional.ofNullable(f.getFolderId()).isPresent()
                        &&  f.getFolderId().equals(comparable)) {
                    result.add(f);
                    resultString.add(f.getFolderName());
                }
            }
        }

        Collections.reverse(resultString);
        StringBuilder sb = new StringBuilder();
        sb.append(ROOT_NAME);
        sb.append(" /");
        for (String s : resultString) {
            sb.append(" ");
            sb.append(s);
            sb.append(" /");
        }

        return sb.toString();
    }
}
