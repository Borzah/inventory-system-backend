package com.demo.inventory.data.service;

import com.demo.inventory.data.dto.FolderResponse;
import com.demo.inventory.data.dto.ItemNodeResponse;
import com.demo.inventory.data.dto.ItemResponse;
import com.demo.inventory.data.utils.InventoryUtils;
import com.demo.inventory.exception.RequestedObjectNotFoundException;
import com.demo.inventory.item.dto.FolderDto;
import com.demo.inventory.item.mapper.CategoryMapper;
import com.demo.inventory.item.mapper.FolderMapper;
import com.demo.inventory.item.model.Category;
import com.demo.inventory.item.model.Folder;
import com.demo.inventory.item.model.Item;
import com.demo.inventory.item.repository.CategoryRepository;
import com.demo.inventory.item.repository.FolderRepository;
import com.demo.inventory.item.repository.ItemRepository;
import com.demo.inventory.item.service.FolderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InventoryService {

    private final ItemRepository itemRepository;
    private final FolderRepository folderRepository;
    private final CategoryRepository categoryRepository;
    private final InventoryUtils inventoryUtils;
    private final FolderMapper folderMapper;

    private static final String ROOTNAME = "My-items";

    public ItemResponse getItemResponseByItemId(Long itemId, Long userId) {
        return itemRepository.findByItemIdAndUserId(itemId, userId)
                .map(inventoryUtils::createItemResponse)
                .orElseThrow(() -> new RequestedObjectNotFoundException(
                        String.format("Item with id [%d] and user id [%d] does not exist",
                                itemId, userId)));
    }

    public FolderResponse getContentBySection(Long userId, Long folderId) {
        List<FolderDto> folders = folderRepository.findAllByUserIdAndParentId(userId, folderId).stream()
                .map(folderMapper::fromFolder)
                .collect(Collectors.toList());

        List<ItemNodeResponse> items = itemRepository.findAllByUserIdAndFolderId(userId, folderId).stream()
                .map(inventoryUtils::createItemNodeResponse)
                .collect(Collectors.toList());

        Long parentFolderId = null;
        String currentFolderPathName = ROOTNAME;

        if (Optional.ofNullable(folderId).isPresent()) {
            Optional<Folder> currentFolderOptional = folderRepository.findById(folderId);
            if (currentFolderOptional.isEmpty()) {
                throw new RequestedObjectNotFoundException(
                        String.format("Folder with id [%d] does not exist", folderId));
            }

            Folder currentFolder = currentFolderOptional.get();
            parentFolderId = currentFolder.getParentId();
            List<Folder> possibleParents = folderRepository
                    .findAllByUserIdAndFolderIdIsLessThan(userId, folderId);
            currentFolderPathName = inventoryUtils.getFolderPathName(possibleParents, currentFolder);
        }

        return inventoryUtils.createFolderResponse(
                folderId,
                parentFolderId,
                currentFolderPathName,
                folders,
                items);
    }

    public Map<String, List<ItemNodeResponse>> getItemsByCategory(Long userId) {
        Map<String, List<ItemNodeResponse>> result = new HashMap<>();
        List<Category> categories = categoryRepository.findAllByUserId(userId);

        categories.forEach(category -> {
            List<ItemNodeResponse> items = itemRepository.findAllByCategoryId(category.getCategoryId()).stream()
                    .map(inventoryUtils::createItemNodeResponse)
                    .collect(Collectors.toList());
            result.put(category.getCategoryName(), items);
        });

        return result;
    }
}
