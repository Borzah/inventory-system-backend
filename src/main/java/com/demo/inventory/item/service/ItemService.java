package com.demo.inventory.item.service;

import com.demo.inventory.item.dto.ItemDto;
import com.demo.inventory.item.model.Item;
import com.demo.inventory.item.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemDto addItem(ItemDto itemDto) {
        Item item = Item
                .builder().
                itemName(itemDto.getItemName()).
                folderId(itemDto.getFolderId()).
                userId(itemDto.getUserId()).
                categoryId(itemDto.getCategoryId()).
                description(itemDto.getDescription())
                .dateAdded(new Date())
                .serialNumber(itemDto.getSerialNumber())
                .itemPrice(itemDto.getItemPrice()).build();
        return convertItem(itemRepository.save(item));
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public ItemDto getItem(Long itemId) {
        return convertItem(itemRepository.findByItemId(itemId));
    }

    public ItemDto updateItem(Long itemId, ItemDto itemDto) {
        Item item = itemRepository.findByItemId(itemId);
        item.setItemName(itemDto.getItemName());
        item.setCategoryId(itemDto.getCategoryId());
        item.setDescription(itemDto.getDescription());
        item.setSerialNumber(itemDto.getSerialNumber());
        item.setItemPrice(itemDto.getItemPrice());
        Item save = itemRepository.save(item);
        return convertItem(save);
    }

    public void deleteItem(Long itemId) {
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
}
