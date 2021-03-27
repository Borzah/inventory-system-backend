package com.demo.inventory.data.service;

import com.demo.inventory.data.dto.FolderResponse;
import com.demo.inventory.data.dto.ItemResponse;
import com.demo.inventory.data.utils.InventoryUtils;
import com.demo.inventory.item.dto.FolderDto;
import com.demo.inventory.item.model.Folder;
import com.demo.inventory.item.model.Item;
import com.demo.inventory.item.repository.FolderRepository;
import com.demo.inventory.item.repository.ItemRepository;
import com.demo.inventory.item.service.FolderService;
import com.demo.inventory.security.AuthChecker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InventoryService {

    private final ItemRepository itemRepository;
    private final FolderRepository folderRepository;
    private final FolderService folderService;
    private final InventoryUtils inventoryUtils;
    private final AuthChecker authChecker;

    public ItemResponse getItemResponseByItemId(Long itemId, String authToken) {
        Item item = itemRepository.findByItemId(itemId);
        authChecker.checkUserAttachingTheirInfo(item.getUserId(), authToken);
        return inventoryUtils.createItemResponse(item);
    }

    public FolderResponse getContentBySection(Long user, Long folder, String authToken) {
        authChecker.checkUserAttachingTheirInfo(user, authToken);
        List<FolderDto> folders = folderRepository.findAllByUserIdAndParentId(user, folder).stream()
                .map(folderService::convertFolder)
                .collect(Collectors.toList());
        List<ItemResponse> items = itemRepository.findAllByUserIdAndFolderId(user, folder).stream()
                .map(inventoryUtils::createItemResponse)
                .collect(Collectors.toList());
        Long currentFolderId = folder;
        Long parentFolderId = null;
        String currentFolderPathName = "My-items";
        String currentFolderName = "My-Items";
        if (folder != null) {
            Folder currentFolder = folderRepository.findByFolderId(folder);
            parentFolderId = currentFolder.getParentId();
            currentFolderName = currentFolder.getFolderName();
            List<Folder> possibleParents = folderRepository.findAllByUserIdAndFolderIdIsLessThan(user, folder);
            currentFolderPathName = inventoryUtils.getFolderPathName(possibleParents, currentFolder);
        }
        return inventoryUtils.createFolderResponse(
                currentFolderId,
                parentFolderId,
                currentFolderName,
                currentFolderPathName,
                folders,
                items);
    }
}
