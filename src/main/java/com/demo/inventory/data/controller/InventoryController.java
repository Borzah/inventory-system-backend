package com.demo.inventory.data.controller;

import com.demo.inventory.data.dto.FolderResponse;
import com.demo.inventory.data.dto.ItemNodeResponse;
import com.demo.inventory.data.dto.ItemResponse;
import com.demo.inventory.data.service.InventoryService;
import com.demo.inventory.data.service.SearchService;
import com.demo.inventory.item.dto.CategoryDto;
import com.demo.inventory.security.Roles;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Secured(Roles.USER)
@RestController
@RequestMapping("inventory")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
    private final SearchService searchService;

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
        return searchService.getAllUsersItemResponses(userId, attribute, search, authToken);
    }

    @GetMapping("categories")
    public Map<String, List<ItemNodeResponse>> getItemsByCategory(@RequestBody List<CategoryDto> categories,
                                                                  @RequestHeader("Authorization") String authToken) {
        return inventoryService.getItemsByCategory(categories, authToken);
    }
}
