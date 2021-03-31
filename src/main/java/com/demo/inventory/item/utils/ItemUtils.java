package com.demo.inventory.item.utils;

import com.demo.inventory.exception.FolderException;
import com.demo.inventory.exception.ItemException;
import com.demo.inventory.exception.RequestedObjectNotFoundException;
import com.demo.inventory.item.dto.ItemDto;
import com.demo.inventory.item.model.Folder;
import com.demo.inventory.item.model.Item;
import com.demo.inventory.item.repository.FolderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class ItemUtils {

    private final FolderRepository folderRepository;

    public void checkUserAddingItemOrFolderIntoTheirFolder(Long folderId, Long userId) {
        List<Folder> result = folderRepository.findAllByFolderIdAndUserId(folderId, userId);
        if (result.size() == 0) {
            throw new FolderException("It is possible to add content only into your folder");
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
