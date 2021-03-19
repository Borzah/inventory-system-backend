package com.demo.inventory.data.controller;

import com.demo.inventory.data.dto.FolderResponse;
import com.demo.inventory.data.dto.ItemResponse;
import com.demo.inventory.data.service.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("inventory")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public FolderResponse getContentBySection(@RequestParam Long user,
                                              @RequestParam(required = false) Long folder) {
        return inventoryService.getContentBySection(user, folder);
    }

    @GetMapping("{itemId}")
    public ItemResponse getItemResponseByItemId(@PathVariable Long itemId) {
        return inventoryService.getItemResponseByItemId(itemId);
    }

    @GetMapping("user/{userId}")
    public List<ItemResponse> getAllUsersItemResponses(@PathVariable Long userId,
                                                       @RequestParam(required = false) String search) {
        return inventoryService.getAllUsersItemResponses(userId, search);
    }
}
