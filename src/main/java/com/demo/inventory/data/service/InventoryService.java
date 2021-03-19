package com.demo.inventory.data.service;

import com.demo.inventory.data.dto.FolderResponse;
import com.demo.inventory.data.dto.ItemResponse;
import com.demo.inventory.item.dto.FolderDto;
import com.demo.inventory.item.model.Category;
import com.demo.inventory.item.model.Folder;
import com.demo.inventory.item.model.Image;
import com.demo.inventory.item.model.Item;
import com.demo.inventory.item.repository.CategoryRepository;
import com.demo.inventory.item.repository.FolderRepository;
import com.demo.inventory.item.repository.ImageRepository;
import com.demo.inventory.item.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InventoryService {

    private final ItemRepository itemRepository;
    private final FolderRepository folderRepository;
    private final ImageRepository imageRepository;
    private final CategoryRepository categoryRepository;

    public ItemResponse getItemResponseByItemId(Long itemId) {
        return createItemResponse(itemRepository.findByItemId(itemId));
    }

    public FolderResponse getContentBySection(Long user, Long folder) {
        List<FolderDto> folders = folderRepository.findAllByUserIdAndParentId(user, folder).stream()
                .map(this::convertFolder)
                .collect(Collectors.toList());
        List<ItemResponse> items = itemRepository.findAllByUserIdAndFolderId(user, folder).stream()
                .map(this::createItemResponse)
                .collect(Collectors.toList());
        Long currentFolderId = folder;
        Long parentFolderId = null;
        String currentFolderPathName = "My-items";
        String currentFolderName = "My-Items";
        if (folder != null) {
            Folder currentFolder = folderRepository.findByFolderId(folder);
            parentFolderId = currentFolder.getParentId();
            currentFolderName = currentFolder.getFolderName();
            currentFolderPathName = currentFolder.getFolderPathName();
        }
        FolderResponse content = new FolderResponse();
        content.setCurrentFolderId(currentFolderId);
        content.setParentFolderId(parentFolderId);
        content.setCurrentFolderName(currentFolderName);
        content.setCurrentFolderPathName(currentFolderPathName);
        content.setFolders(folders);
        content.setItems(items);
        return content;
    }

    // search by date not supported
    public List<ItemResponse> getAllUsersItemResponses(Long userId, String search) {
        List<ItemResponse> items = itemRepository.findAllByUserId(userId).stream()
                .map(this::createItemResponse)
                .collect(Collectors.toList());
        if (search == null) {
            return items;
        }
        String replacedInput = search.replace("_", " ");
        List<ItemResponse> result = new ArrayList<>();
        items.forEach(item -> {
            if (isMatchedWithCriteria(replacedInput, item)) {
                result.add(item);
            }
        });
        return result;
    }

    private boolean isMatchedWithCriteria(String search, ItemResponse item) {
        return item.getItemName().equalsIgnoreCase(search.toLowerCase()) || item.getFolderName() != null
                && item.getFolderName().equalsIgnoreCase(search.toLowerCase()) ||
                item.getCategoryName() != null && item.getCategoryName().equalsIgnoreCase(search.toLowerCase())
                || item.getDescription() != null &&
                item.getDescription().toLowerCase().contains(search.toLowerCase()) ||
                item.getSerialNumber() != null && item.getSerialNumber().equalsIgnoreCase(search.toLowerCase());
    }

    private ItemResponse createItemResponse(Item item) {
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

    private FolderDto convertFolder(Folder folder) {
        FolderDto folderDto = new FolderDto();
        folderDto.setFolderId(folder.getFolderId());
        folderDto.setFolderName(folder.getFolderName());
        folderDto.setFolderPathName(folder.getFolderPathName());
        folderDto.setUserId(folder.getUserId());
        folderDto.setParentId(folder.getParentId());
        return folderDto;
    }
}
