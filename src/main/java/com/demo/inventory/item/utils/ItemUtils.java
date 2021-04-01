package com.demo.inventory.item.utils;

import com.demo.inventory.exception.AuthorizationException;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
@Slf4j
public class ItemUtils {

    private final FolderRepository folderRepository;
    private final CategoryRepository categoryRepository;

    public void checkUserAddingItemOrFolderIntoTheirFolder(Long folderId, Long userId) {
        Optional<Folder> result = folderRepository.findById(folderId);
        if (result.isEmpty()) {
            throw new FolderException(
                    "Folder into which you are trying to add does not exist.");
        }
        if (!result.get().getUserId().equals(userId)) {
            AuthorizationException e =
                    new AuthorizationException("It is possible to add content only into your folder");
            log.warn("User with id {} tried to add content into other user folder", userId, e);
            throw  e;
        }
    }

    public void checkUserIsAddingItemToTheirCategory(Long categoryId, Long userId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isEmpty()) {
            throw new ItemException(String.format(
                    "Category with id [%d]to which you are trying to add item does not exist",
                    categoryId));
        }
        if (!categoryOptional.get().getUserId().equals(userId)) {
            AuthorizationException e =
                    new AuthorizationException("It is possible to add item only to your category");
            log.warn("User with id {} tried to add content into other user category", userId, e);
            throw e;
        }
    }

    public void checkNamingRegex(List<String> namings) {
        namings.forEach(naming -> {
            if (Optional.ofNullable(naming).isPresent() && naming.contains("_")) {
                throw new ItemException("Attribute name cannot contain '_' sign");
            }
        });
    }

    public void checkIfItemIsEmpty(Optional<Item> itemOptional, Long itemId) {
        if (itemOptional.isEmpty()) {
            throw new RequestedObjectNotFoundException(
                    String.format("Item with id [%d] does not exist", itemId));
        }
    }

    public void checkNamings(ItemDto itemDto) {
        List<String> namings = new ArrayList<>();
        namings.add(itemDto.getItemName());

        if (itemDto.getSerialNumber() != null) namings.add(itemDto.getSerialNumber());
        if (itemDto.getDescription() != null) namings.add(itemDto.getDescription());

        checkNamingRegex(namings);
    }
}
