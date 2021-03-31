package com.demo.inventory.data.service;

import com.demo.inventory.data.dto.FolderResponse;
import com.demo.inventory.data.dto.ItemNodeResponse;
import com.demo.inventory.data.dto.ItemResponse;
import com.demo.inventory.data.utils.InventoryUtils;
import com.demo.inventory.exception.RequestedObjectNotFoundException;
import com.demo.inventory.item.dto.FolderDto;
import com.demo.inventory.item.model.Category;
import com.demo.inventory.item.model.Folder;
import com.demo.inventory.item.model.Item;
import com.demo.inventory.item.repository.CategoryRepository;
import com.demo.inventory.item.repository.FolderRepository;
import com.demo.inventory.item.repository.ItemRepository;
import com.demo.inventory.item.service.FolderService;
import com.demo.inventory.security.AuthChecker;
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
    private final FolderService folderService;
    private final InventoryUtils inventoryUtils;
    private final AuthChecker authChecker;

    public ItemResponse getItemResponseByItemId(Long itemId, String authToken) {
        Optional<Item> itemOptional = itemRepository.findById(itemId);

        if (itemOptional.isPresent()) {
            Item item = itemOptional.get();
            authChecker.checkUserAttachingTheirInfo(item.getUserId(), authToken);
            return inventoryUtils.createItemResponse(item);
        }
        throw new RequestedObjectNotFoundException(
                String.format("Item with id [%d] does not exist", itemId));
    }

    public FolderResponse getContentBySection(Long user, Long folder, String authToken) {
        authChecker.checkUserAttachingTheirInfo(user, authToken);

        List<FolderDto> folders = folderRepository.findAllByUserIdAndParentId(user, folder).stream()
                .map(folderService::convertFolder)
                .collect(Collectors.toList());

        List<ItemNodeResponse> items = itemRepository.findAllByUserIdAndFolderId(user, folder).stream()
                .map(inventoryUtils::createItemNodeResponse)
                .collect(Collectors.toList());

        Long parentFolderId = null;
        String currentFolderPathName = "My-items";

        if (Optional.ofNullable(folder).isPresent()) {
            Optional<Folder> currentFolderOptional = folderRepository.findById(folder);
            if (currentFolderOptional.isEmpty()) {
                throw new RequestedObjectNotFoundException(
                        String.format("Folder with id [%d] does not exist", folder));
            }

            Folder currentFolder = currentFolderOptional.get();
            parentFolderId = currentFolder.getParentId();
            List<Folder> possibleParents = folderRepository
                    .findAllByUserIdAndFolderIdIsLessThan(user, folder);
            currentFolderPathName = inventoryUtils.getFolderPathName(possibleParents, currentFolder);
        }

        return inventoryUtils.createFolderResponse(
                folder,
                parentFolderId,
                currentFolderPathName,
                folders,
                items);
    }

    public Map<String, List<ItemNodeResponse>> getItemsByCategory(Long userId,
                                                                  String authToken) {
        Map<String, List<ItemNodeResponse>> result = new HashMap<>();
        List<Category> categories = categoryRepository.findAllByUserId(userId);

        categories.forEach(category -> {
            authChecker.checkUserAttachingTheirInfo(category.getUserId(), authToken);
            List<ItemNodeResponse> items = itemRepository.findAllByCategoryId(category.getCategoryId()).stream()
                    .map(inventoryUtils::createItemNodeResponse)
                    .collect(Collectors.toList());
            result.put(category.getCategoryName(), items);
        });

        return result;
    }
}
