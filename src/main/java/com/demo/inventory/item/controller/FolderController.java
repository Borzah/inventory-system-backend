package com.demo.inventory.item.controller;

import com.demo.inventory.item.dto.FolderDto;
import com.demo.inventory.item.service.FolderService;
import com.demo.inventory.security.InventoryUser;
import com.demo.inventory.security.Roles;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Secured(Roles.USER)
@RestController
@RequestMapping("folder")
@AllArgsConstructor
public class FolderController {

    private final FolderService folderService;

    @GetMapping("all")
    public List<FolderDto> getAllUserFolders(@AuthenticationPrincipal InventoryUser auth) {
        return folderService.getAllUserFolders(auth.getId());
    }

    @PostMapping
    public FolderDto addFolder(@Valid @RequestBody FolderDto folderDto,
                               @AuthenticationPrincipal InventoryUser auth) {
        return folderService.addFolder(folderDto, auth.getId());
    }

    @DeleteMapping("{folderId}")
    public void deleteFolder(@PathVariable Long folderId,
                             @AuthenticationPrincipal InventoryUser auth) {
        folderService.deleteFolder(folderId, auth.getId());
    }
}
