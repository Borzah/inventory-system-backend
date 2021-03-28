package com.demo.inventory.data.utils;

import com.demo.inventory.data.dto.FolderResponse;
import com.demo.inventory.data.dto.ItemNodeResponse;
import com.demo.inventory.data.dto.ItemResponse;
import com.demo.inventory.item.dto.FolderDto;
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

@Component
@AllArgsConstructor
public class InventoryUtils {

    private final ImageRepository imageRepository;
    private final CategoryRepository categoryRepository;
    private final FolderRepository folderRepository;

    public ItemNodeResponse createItemNodeResponse(Item item) {
        return new ItemNodeResponse(item.getItemId(), item.getItemName());
    }

    public ItemResponse createItemResponse(Item item) {
        byte[] imageBytes = new byte[]{};
        String categoryName = null;
        String folderName = null;
        Image image = imageRepository.findByImageId(item.getItemId());
        if (image != null) {
            imageBytes = image.getImageBytes();
        }
        if (item.getCategoryId() != null) {
            Category category = categoryRepository.findByCategoryId(item.getCategoryId());
            categoryName = category.getCategoryName();
        }
        if (item.getFolderId() != null) {
            Folder folder = folderRepository.findByFolderId(item.getFolderId());
            folderName = folder.getFolderName();
        }
        return ItemResponse.builder()
                .itemId(item.getItemId())
                .itemName(item.getItemName())
                .folderId(item.getFolderId())
                .folderName(folderName)
                .userId(item.getUserId())
                .dateAdded(item.getDateAdded())
                .description(item.getDescription())
                .serialNumber(item.getSerialNumber())
                .categoryName(categoryName)
                .imageBytes(imageBytes)
                .itemPrice(item.getItemPrice()).build();
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
                if (f.getFolderId() != null  &&  f.getFolderId().equals(comparable)) {
                    result.add(f);
                    resultString.add(f.getFolderName());
                }
            }
        }
        Collections.reverse(resultString);
        StringBuilder sb = new StringBuilder();
        sb.append("My-Items/");
        for (String s : resultString) {
            sb.append(s);
            sb.append("/");
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
