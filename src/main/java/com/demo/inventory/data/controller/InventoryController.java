package com.demo.inventory.data.controller;

import com.demo.inventory.data.dto.FolderResponse;
import com.demo.inventory.data.dto.ItemNodeResponse;
import com.demo.inventory.data.dto.ItemResponse;
import com.demo.inventory.data.service.InventoryService;
import com.demo.inventory.data.service.SearchService;
import com.demo.inventory.security.InventoryUser;
import com.demo.inventory.security.Roles;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Secured(Roles.USER)
@RestController
@RequestMapping("inventory")
@AllArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
    private final SearchService searchService;

    @GetMapping
    public FolderResponse getContentBySection(@RequestParam(required = false) Long folderId,
                                              @AuthenticationPrincipal InventoryUser auth) {
        return inventoryService.getContentBySection(auth.getId(), folderId);
    }

    @GetMapping("{itemId}")
    public ItemResponse getItemResponseByItemId(@PathVariable Long itemId,
                                                @AuthenticationPrincipal InventoryUser auth) {
        return inventoryService.getItemResponseByItemId(itemId, auth.getId());
    }

    @GetMapping("user/categories")
    public Map<String, List<ItemNodeResponse>> getItemsByCategory(@AuthenticationPrincipal InventoryUser auth) {
        return inventoryService.getItemsByCategory(auth.getId());
    }

    @GetMapping("user")
    public List<ItemNodeResponse> getAllUsersItemNodes(@RequestParam(required = false) String attribute,
                                                       @RequestParam(required = false) String search,
                                                       @AuthenticationPrincipal InventoryUser auth) {
        return searchService.getAllUsersItemNodes(auth.getId(), attribute, search);
    }
}
