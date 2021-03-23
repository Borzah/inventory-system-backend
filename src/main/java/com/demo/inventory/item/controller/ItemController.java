package com.demo.inventory.item.controller;

import com.demo.inventory.item.dto.ItemDto;
import com.demo.inventory.item.model.Item;
import com.demo.inventory.item.service.ItemService;
import com.demo.inventory.security.Roles;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Secured(Roles.USER)
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

    @GetMapping("{itemId}")
    public ItemDto getItem(@PathVariable Long itemId,
                           @RequestHeader("Authorization") String authToken) {
        return itemService.getItem(itemId, authToken);
    }

    @PostMapping
    public ItemDto addItem(@RequestBody ItemDto itemDto,
                           @RequestHeader("Authorization") String authToken) {
        return itemService.addItem(itemDto, authToken);
    }

    @PutMapping("{itemId}")
    public ItemDto updateItem(@PathVariable Long itemId,
                              @RequestBody ItemDto itemDto,
                              @RequestHeader("Authorization") String authToken) {
        return itemService.updateItem(itemId, itemDto, authToken);
    }

    @DeleteMapping("{itemId}")
    public void deleteItem(@PathVariable Long itemId,
                           @RequestHeader("Authorization") String authToken) {
        itemService.deleteItem(itemId, authToken);
    }
}
