package com.demo.inventory.item.controller;

import com.demo.inventory.item.dto.FolderDto;
import com.demo.inventory.item.service.FolderService;
import com.demo.inventory.security.InventoryUser;
import com.demo.inventory.security.Roles;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Secured(Roles.USER)
@RestController
@RequestMapping("folders")
@AllArgsConstructor
public class FolderController {

    private final FolderService folderService;

    @GetMapping
    public List<FolderDto> getAllUserFolders(@AuthenticationPrincipal InventoryUser auth) {
        return folderService.getAllUserFolders(auth.getId());
    }

    @PostMapping
    public ResponseEntity<FolderDto> addFolder(@Valid @RequestBody FolderDto folderDto,
                                               @AuthenticationPrincipal InventoryUser auth) {
        FolderDto folder = folderService.addFolder(folderDto, auth.getId());
        return new ResponseEntity<>(folder, HttpStatus.CREATED);
    }

    @DeleteMapping("{folderId}")
    public ResponseEntity<Void> deleteFolder(@PathVariable Long folderId,
                                             @AuthenticationPrincipal InventoryUser auth) {
        folderService.deleteFolder(folderId, auth.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
