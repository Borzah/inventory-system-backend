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

    public Item addItem(ItemDto itemDto) {
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
        return itemRepository.save(item);
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
}
