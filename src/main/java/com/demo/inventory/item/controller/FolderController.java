package com.demo.inventory.item.controller;

import com.demo.inventory.item.dto.FolderDto;
import com.demo.inventory.item.service.FolderService;
import com.demo.inventory.security.Roles;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Secured(Roles.USER)
@RestController
@RequestMapping("folders")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class FolderController {

    private final FolderService folderService;

    @PostMapping
    public FolderDto addFolder(@Valid @RequestBody FolderDto folderDto,
                               @RequestHeader("Authorization") String authToken) {
        return folderService.addFolder(folderDto, authToken);
    }

    @DeleteMapping("{folderId}")
    public void deleteFolder(@PathVariable Long folderId,
                             @RequestHeader("Authorization") String authToken) {
        folderService.deleteFolder(folderId, authToken);
    }
}
