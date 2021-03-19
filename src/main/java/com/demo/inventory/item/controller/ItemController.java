package com.demo.inventory.item.controller;

import com.demo.inventory.item.dto.ItemDto;
import com.demo.inventory.item.model.Item;
import com.demo.inventory.item.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("items")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @PostMapping
    public Item addItem(@RequestBody ItemDto itemDto) {
        return itemService.addItem(itemDto);
    }

    @DeleteMapping("{itemId}")
    public void deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
    }
}
