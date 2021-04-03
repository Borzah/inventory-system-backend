package com.demo.inventory.data.utils;

import com.demo.inventory.data.dto.FolderResponse;
import com.demo.inventory.data.dto.ItemNodeResponse;
import com.demo.inventory.data.dto.ItemResponse;
import com.demo.inventory.data.mapper.ItemResponseMapper;
import com.demo.inventory.item.dto.FolderDto;
import com.demo.inventory.item.mapper.CategoryMapper;
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
            Category category = categoryRepository.findByCategoryId(item.getCategoryId());
            categoryName = category.getCategoryName();
        }

        if (Optional.ofNullable(item.getFolderId()).isPresent()) {
            Folder folder = folderRepository.findByFolderId(item.getFolderId());
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
        sb.append("My-Items /");
        for (String s : resultString) {
            sb.append(" ");
            sb.append(s);
            sb.append(" /");
        }

        return sb.toString();
    }

    public FolderResponse createFolderResponse(Long currentFolderId,
                                               Long parentFolderId,
                                               String currentFolderPathName,
                                               List<FolderDto> folders,
                                               List<ItemNodeResponse> items) {
        return FolderResponse.builder()
                .currentFolderId(currentFolderId)
                .parentFolderId(parentFolderId)
                .currentFolderPathName(currentFolderPathName)
                .folders(folders)
                .items(items).build();
    }
}
