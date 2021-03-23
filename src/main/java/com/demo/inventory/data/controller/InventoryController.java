package com.demo.inventory.data.controller;

import com.demo.inventory.data.dto.FolderResponse;
import com.demo.inventory.data.dto.ItemResponse;
import com.demo.inventory.data.service.InventoryService;
import com.demo.inventory.security.Roles;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Secured(Roles.USER)
@RestController
@RequestMapping("inventory")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public FolderResponse getContentBySection(@RequestParam Long user,
                                              @RequestParam(required = false) Long folder,
                                              @RequestHeader("Authorization") String authToken) {
        return inventoryService.getContentBySection(user, folder, authToken);
    }

    @GetMapping("{itemId}")
    public ItemResponse getItemResponseByItemId(@PathVariable Long itemId,
                                                @RequestHeader("Authorization") String authToken) {
        return inventoryService.getItemResponseByItemId(itemId, authToken);
    }

    @GetMapping("user/{userId}")
    public List<ItemResponse> getAllUsersItemResponses(@PathVariable Long userId,
                                                       @RequestParam(required = false) String attribute,
                                                       @RequestParam(required = false) String search,
                                                       @RequestHeader("Authorization") String authToken) {
        return inventoryService.getAllUsersItemResponses(userId, attribute, search, authToken);
    }
}
