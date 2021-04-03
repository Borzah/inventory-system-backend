package com.demo.inventory.item.service;

import com.demo.inventory.item.utils.ItemUtils;
import com.demo.inventory.item.dto.ItemDto;
import com.demo.inventory.item.model.Item;
import com.demo.inventory.item.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemUtils itemUtils;

    public ItemDto addItem(ItemDto itemDto, Long userId) {

        performAllItemChecks(itemDto, userId);

        Item item = createItemFromDto(itemDto);
        item.setUserId(userId);
        item.setDateAdded(new Timestamp(System.currentTimeMillis()));

        return convertItem(itemRepository.save(item));
    }

    public ItemDto getItem(Long itemId, Long userId) {
        Optional<Item> itemOptional = itemRepository.findByItemIdAndUserId(itemId, userId);
        itemUtils.checkIfItemIsEmpty(itemOptional, itemId);

        // optional isPresent is checked in utils method
        Item item = itemOptional.get();

        return convertItem(item);
    }

    public ItemDto updateItem(Long itemId, ItemDto itemDto, Long userId) {
        Optional<Item> itemOptional = itemRepository.findByItemIdAndUserId(itemId, userId);

        itemUtils.checkIfItemIsEmpty(itemOptional, itemId);

        // optional isPresent is checked in utils method
        Item item = itemOptional.get();
        Timestamp dateAdded = item.getDateAdded();

        performAllItemChecks(itemDto, userId);

        item = createItemFromDto(itemDto);
        item.setItemId(itemId);
        item.setUserId(userId);
        item.setDateAdded(dateAdded);

        return convertItem(itemRepository.save(item));
    }

    public void deleteItem(Long itemId, Long userId) {
        Optional<Item> itemOptional = itemRepository.findByItemIdAndUserId(itemId, userId);

        itemUtils.checkIfItemIsEmpty(itemOptional, itemId);

        itemRepository.deleteById(itemId);
    }

    private void performAllItemChecks(ItemDto itemDto, Long userId) {
        itemUtils.checkNamings(itemDto);

        if (Optional.ofNullable(itemDto.getFolderId()).isPresent()) {
            itemUtils.checkUserAddingItemOrFolderIntoTheirFolder(itemDto.getFolderId(), userId);
        }

        if (Optional.ofNullable(itemDto.getCategoryId()).isPresent()) {
            itemUtils.checkUserIsAddingItemToTheirCategory(itemDto.getCategoryId(), userId);
        }
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
                .itemPrice(item.getItemPrice())
                .build();
    }

    private Item createItemFromDto(ItemDto itemDto) {
        return Item
                .builder()
                .itemName(itemDto.getItemName())
                .folderId(itemDto.getFolderId())
                .userId(itemDto.getUserId())
                .categoryId(itemDto.getCategoryId())
                .description(itemDto.getDescription())
                .serialNumber(itemDto.getSerialNumber())
                .itemPrice(itemDto.getItemPrice())
                .build();
    }
}
