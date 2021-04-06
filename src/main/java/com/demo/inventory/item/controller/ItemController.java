package com.demo.inventory.item.controller;

import com.demo.inventory.item.dto.ItemDto;
import com.demo.inventory.item.service.ItemService;
import com.demo.inventory.security.InventoryUser;
import com.demo.inventory.security.Roles;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Secured(Roles.USER)
@RestController
@RequestMapping("items")
@AllArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("{itemId}")
    public ItemDto getItem(@PathVariable Long itemId,
                           @AuthenticationPrincipal InventoryUser auth) {
        return itemService.getItem(itemId, auth.getId());
    }

    @PostMapping
    public ResponseEntity<ItemDto> addItem(@Valid @RequestBody ItemDto itemDto,
                                           @AuthenticationPrincipal InventoryUser auth) {
        ItemDto item = itemService.addItem(itemDto, auth.getId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PutMapping("{itemId}")
    public ItemDto updateItem(@PathVariable Long itemId,
                              @Valid @RequestBody ItemDto itemDto,
                              @AuthenticationPrincipal InventoryUser auth) {
        return itemService.updateItem(itemId, itemDto, auth.getId());
    }

    @DeleteMapping("{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId,
                                           @AuthenticationPrincipal InventoryUser auth) {
        itemService.deleteItem(itemId, auth.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
