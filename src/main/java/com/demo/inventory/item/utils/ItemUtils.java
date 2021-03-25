package com.demo.inventory.item.utils;

import com.demo.inventory.exception.FolderException;
import com.demo.inventory.exception.ItemException;
import com.demo.inventory.item.model.Folder;
import com.demo.inventory.item.repository.FolderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

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
            if (naming != null && naming.contains("_")) {
                throw new ItemException("Attribute name cannot contain '_' sign");
            }
        });
    }
}
