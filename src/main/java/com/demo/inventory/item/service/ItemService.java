package com.demo.inventory.item.service;

import com.demo.inventory.exception.ItemException;
import com.demo.inventory.exception.RequestedObjectNotFoundException;
import com.demo.inventory.item.utils.ItemUtils;
import com.demo.inventory.item.dto.ItemDto;
import com.demo.inventory.item.model.Item;
import com.demo.inventory.item.repository.ItemRepository;
import com.demo.inventory.security.AuthChecker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final AuthChecker authChecker;
    private final ItemUtils itemUtils;

    public ItemDto addItem(ItemDto itemDto, String authToken) {
        Long userId = itemDto.getUserId();
        authChecker.checkUserAttachingTheirInfo(userId, authToken);
        itemUtils.checkNamings(itemDto);
        if (Optional.ofNullable(itemDto.getFolderId()).isPresent()) {
            itemUtils.checkUserAddingItemOrFolderIntoTheirFolder(itemDto.getFolderId(), userId);
        }
        Item item = createItemFromDto(itemDto);
        return convertItem(itemRepository.save(item));
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public ItemDto getItem(Long itemId, String authToken) {
        Optional<Item> itemOptional = itemRepository.findById(itemId);
        itemUtils.checkIfItemIsEmpty(itemOptional, itemId);
        Item item = itemOptional.get();
        Long userId = item.getUserId();
        authChecker.checkUserAttachingTheirInfo(userId, authToken);
        return convertItem(item);
    }

    public ItemDto updateItem(Long itemId, ItemDto itemDto, String authToken) {
        Optional<Item> itemOptional = itemRepository.findById(itemId);
        itemUtils.checkIfItemIsEmpty(itemOptional, itemId);
        Item item = itemOptional.get();
        Long userId = item.getUserId();
        authChecker.checkUserAttachingTheirInfo(userId, authToken);
        itemUtils.checkNamings(itemDto);
        if (Optional.ofNullable(itemDto.getFolderId()).isPresent()) {
            itemUtils.checkUserAddingItemOrFolderIntoTheirFolder(itemDto.getFolderId(), userId);
        }
        item = createItemFromDto(itemDto);
        item.setItemId(itemId);
        return convertItem(itemRepository.save(item));
    }

    public void deleteItem(Long itemId, String authToken) {
        Optional<Item> itemOptional = itemRepository.findById(itemId);
        itemUtils.checkIfItemIsEmpty(itemOptional, itemId);
        Long userId = itemOptional.get().getUserId();
        authChecker.checkUserAttachingTheirInfo(userId, authToken);
        itemRepository.deleteById(itemId);
    }

    private ItemDto convertItem(Item item) {
        return ItemDto.builder()
                .itemId(item.getItemId())
                .itemName(item.getItemName())
                .folderId(item.getFolderId())
                .userId(item.getUserId())
                .categoryId(item.getCategoryId())
                .dateAdded(item.getDateAdded())
                .description(item.getDescription())
                .serialNumber(item.getSerialNumber())
                .itemPrice(item.getItemPrice()).build();
    }

    private Item createItemFromDto(ItemDto itemDto) {
        return Item
                .builder()
                .itemName(itemDto.getItemName())
                .folderId(itemDto.getFolderId())
                .userId(itemDto.getUserId())
                .categoryId(itemDto.getCategoryId())
                .description(itemDto.getDescription())
                .dateAdded(new Timestamp(System.currentTimeMillis()))
                .serialNumber(itemDto.getSerialNumber())
                .itemPrice(itemDto.getItemPrice()).build();
    }
}
