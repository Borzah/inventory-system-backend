package com.demo.inventory.item.service;

import com.demo.inventory.exception.RequestedObjectNotFoundException;
import com.demo.inventory.item.mapper.ItemMapper;
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
    private final ItemMapper mapper;

    public ItemDto addItem(ItemDto itemDto, Long userId) {

        performAllItemChecks(itemDto, userId);

        Item item = mapper.toItem(itemDto);
        item.setUserId(userId);
        item.setDateAdded(new Timestamp(System.currentTimeMillis()));

        return mapper.fromItem(itemRepository.save(item));
    }

    public ItemDto getItem(Long itemId, Long userId) {
        return itemRepository.findByItemIdAndUserId(itemId, userId)
                .map(mapper::fromItem)
                .orElseThrow(() -> new RequestedObjectNotFoundException(
                        String.format(
                                "Item with id [%d] and user id [%d]  does not exist",
                                itemId, userId)
                ));
    }

    public ItemDto updateItem(Long itemId, ItemDto itemDto, Long userId) {
        Optional<Item> itemOptional = itemRepository.findByItemIdAndUserId(itemId, userId);

        itemUtils.checkIfItemIsEmpty(itemOptional, itemId);

        // optional isPresent is checked in utils method
        Item item = itemOptional.get();
        Timestamp dateAdded = item.getDateAdded();

        performAllItemChecks(itemDto, userId);

        item = mapper.toItem(itemDto);
        item.setItemId(itemId);
        item.setUserId(userId);
        item.setDateAdded(dateAdded);

        return mapper.fromItem(itemRepository.save(item));
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
}
