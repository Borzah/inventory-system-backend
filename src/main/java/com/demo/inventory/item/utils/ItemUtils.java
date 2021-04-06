package com.demo.inventory.item.utils;

import com.demo.inventory.exception.FolderException;
import com.demo.inventory.exception.ItemException;
import com.demo.inventory.exception.RequestedObjectNotFoundException;
import com.demo.inventory.item.dto.ItemDto;
import com.demo.inventory.item.model.Category;
import com.demo.inventory.item.model.Folder;
import com.demo.inventory.item.model.Item;
import com.demo.inventory.item.repository.CategoryRepository;
import com.demo.inventory.item.repository.FolderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ItemUtils {

    private final FolderRepository folderRepository;
    private final CategoryRepository categoryRepository;

    public void checkUserAddingItemOrFolderIntoTheirFolder(Long folderId, Long userId) {
        Optional<Folder> result = folderRepository.findByFolderIdAndUserId(folderId, userId);
        if (result.isEmpty()) {
            throw new FolderException(
                    "Folder into which you are trying to add does not exist.");
        }
    }

    public void checkUserIsAddingItemToTheirCategory(Long categoryId, Long userId) {
        Optional<Category> categoryOptional = categoryRepository.findByCategoryIdAndUserId(categoryId, userId);
        if (categoryOptional.isEmpty()) {
            throw new ItemException(String.format(
                    "Category with id [%d] and user id [%d] to which you are trying to add item does not exist",
                    categoryId, userId));
        }
    }

    public void checkNamingRegex(String naming) {
        if (naming.contains("_")) {
            throw new ItemException("Attribute name cannot contain '_' sign");
        }
    }

    public void checkIfItemIsEmpty(Optional<Item> itemOptional, Long itemId) {
        if (itemOptional.isEmpty()) {
            throw new RequestedObjectNotFoundException(
                    String.format("Item with id [%d] does not exist", itemId));
        }
    }

    public void checkNamings(ItemDto itemDto) {
        checkNamingRegex(itemDto.getItemName());
        if (itemDto.getSerialNumber() != null) checkNamingRegex(itemDto.getSerialNumber());
        if (itemDto.getDescription() != null) checkNamingRegex(itemDto.getDescription());
    }
}
