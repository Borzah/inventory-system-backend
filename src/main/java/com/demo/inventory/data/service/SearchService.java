package com.demo.inventory.data.service;

import com.demo.inventory.data.dto.ItemNodeResponse;
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

    public List<ItemNodeResponse> getAllUsersItemNodes(Long userId, String attribute, String search, String authToken) {
        authChecker.checkUserAttachingTheirInfo(userId, authToken);
        if (attribute == null) attribute = "";
        List<Item> items = new ArrayList<>();
        switch (attribute) {
            case "category":
                if (search != null) {
                    items = itemRepository.searchForItemsByCategory(userId, search.toLowerCase());
                } else {
                    items = itemRepository.findAllByUserIdAndCategoryIdNotNull(userId);
                }
                break;
            case "serialNumber":
                if (search != null) {
                    items = itemRepository.searchForSerialNumberContainingAndUserId(search.toLowerCase(), userId);
                } else {
                    items = itemRepository.findAllByUserIdAndSerialNumberNotNull(userId);
                }
                break;
            case "description":
                if (search != null) {
                    items = itemRepository.searchForDescriptionContainingAndUserId(search.toLowerCase(), userId);
                } else {
                    items = itemRepository.findAllByUserIdAndDescriptionNotNull(userId);
                }
                break;
            case "price":
                if (search != null) {
                    items = itemRepository.searchForItemPriceAndUserId(search, userId);
                } else {
                    items = itemRepository.findAllByUserIdAndItemPriceNotNull(userId);
                }
                break;
            case "name":
                if (search != null) {
                    items = itemRepository.searchForItemNameContainingAndUserId(search.toLowerCase(), userId);
                } else {
                    items = itemRepository.findAllByUserId(userId);
                }
                break;
            default:
                if (search != null) {
                    items = itemRepository.searchForItemsByALlFields(userId, search.toLowerCase());
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
