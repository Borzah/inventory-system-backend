package com.demo.inventory.data.service;

import com.demo.inventory.data.dto.ItemNodeResponse;
import com.demo.inventory.data.utils.InventoryUtils;
import com.demo.inventory.item.model.Item;
import com.demo.inventory.item.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SearchService {

    private final ItemRepository itemRepository;
    private final InventoryUtils inventoryUtils;

    public List<ItemNodeResponse> getAllUsersItemNodes(Long userId, String attribute, String search) {
        if (Optional.ofNullable(attribute).isEmpty()) attribute = "";
        List<Item> items;
        switch (attribute) {
            case "category":
                if (Optional.ofNullable(search).isPresent()) {
                    items = itemRepository.searchForItemsByCategory(userId, search.toLowerCase());
                } else {
                    items = itemRepository.findAllByUserIdAndCategoryIdNotNull(userId);
                }
                break;
            case "serialNumber":
                if (Optional.ofNullable(search).isPresent()) {
                    items = itemRepository.searchForSerialNumberContainingAndUserId(search.toLowerCase(), userId);
                } else {
                    items = itemRepository.findAllByUserIdAndSerialNumberNotNull(userId);
                }
                break;
            case "description":
                if (Optional.ofNullable(search).isPresent()) {
                    items = itemRepository.searchForDescriptionContainingAndUserId(search.toLowerCase(), userId);
                } else {
                    items = itemRepository.findAllByUserIdAndDescriptionNotNull(userId);
                }
                break;
            case "price":
                if (Optional.ofNullable(search).isPresent()) {
                    items = itemRepository.searchForItemPriceAndUserId(search, userId);
                } else {
                    items = itemRepository.findAllByUserIdAndItemPriceNotNull(userId);
                }
                break;
            case "folder":
                if (Optional.ofNullable(search).isPresent()) {
                    items = itemRepository.searchForItemsByFolder(userId, search.toLowerCase());
                } else {
                    items = itemRepository.findAllByUserIdAndFolderIdNotNull(userId);
                }
                break;
            default:
                if (Optional.ofNullable(search).isPresent()) {
                    items = itemRepository.searchForItemNameContainingAndUserId(search.toLowerCase(), userId);
                } else {
                    items = itemRepository.findAllByUserId(userId);
                }
                break;
        }

        return items.stream()
                .map(inventoryUtils::createItemNodeResponse)
                .collect(Collectors.toList());
    }
}
