package com.demo.inventory.data.service;

import com.demo.inventory.data.dto.ItemResponse;
import com.demo.inventory.data.utils.InventoryUtils;
import com.demo.inventory.item.model.Item;
import com.demo.inventory.item.repository.ItemRepository;
import com.demo.inventory.security.AuthChecker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SearchService {

    private final ItemRepository itemRepository;
    private final InventoryUtils inventoryUtils;
    private final AuthChecker authChecker;

    public List<ItemResponse> getAllUsersItemResponses(Long userId, String attribute, String search, String authToken) {
        authChecker.checkUserAttachingTheirInfo(userId, authToken);
        List<ItemResponse> items = getItemsByAttributeAndUserId(userId, "name");
        List<ItemResponse> result = new ArrayList<>();
        if (attribute != null) {
            items = getItemsByAttributeAndUserId(userId, attribute);
            if (search != null) {
                return searchAndReturnItems(search, items, result, attribute);
            }
        }
        if (search == null) {
            return items;
        }
        return searchAndReturnItems(search, items, result, "all");
    }

    private List<ItemResponse> getItemsByAttributeAndUserId(Long userId, String parameter) {
        List<Item> items = new ArrayList<>();
        switch (parameter) {
            case "category":
                items = itemRepository.findAllByUserIdAndCategoryIdNotNull(userId);
                break;
            case "serialNumber":
                items = itemRepository.findAllByUserIdAndSerialNumberNotNull(userId);
                break;
            case "description":
                items = itemRepository.findAllByUserIdAndDescriptionNotNull(userId);
                break;
            case "price":
                items = itemRepository.findAllByUserIdAndItemPriceNotNull(userId);
                break;
            default:
                items = itemRepository.findAllByUserId(userId);
                break;
        }
        return items.stream()
                .map(inventoryUtils::createItemResponse)
                .collect(Collectors.toList());
    }

    private List<ItemResponse> searchAndReturnItems(String search, List<ItemResponse> items, List<ItemResponse> result, String parameter) {
        String replacedInput = search.replace("_", " ");
        items.forEach(item -> {
            if (isMatchedWithCriteria(replacedInput, item, parameter)) {
                result.add(item);
            }
        });
        return result;
    }

    private boolean isMatchedWithCriteria(String search, ItemResponse item, String parameter) {
        switch (parameter) {
            case "category":
                return item.getCategoryName().toLowerCase().contains(search.toLowerCase());
            case "serialNumber":
                return item.getSerialNumber().toLowerCase().contains(search.toLowerCase());
            case "description":
                return item.getDescription().toLowerCase().contains(search.toLowerCase());
            case "price":
                return Float.toString(item.getItemPrice()).equals(search);
            case "name":
                return item.getItemName().toLowerCase().contains(search.toLowerCase());
            default:
                return item.getItemName().toLowerCase().contains(search.toLowerCase()) || item.getFolderName() != null
                        && item.getFolderName().toLowerCase().contains(search.toLowerCase()) ||
                        item.getCategoryName() != null && item.getCategoryName().toLowerCase().contains(search.toLowerCase())
                        || item.getDescription() != null &&
                        item.getDescription().toLowerCase().contains(search.toLowerCase()) ||
                        item.getSerialNumber() != null && item.getSerialNumber().toLowerCase().contains(search.toLowerCase())
                        || item.getItemPrice() != null && Float.toString(item.getItemPrice()).contains(search);
        }
    }
}
