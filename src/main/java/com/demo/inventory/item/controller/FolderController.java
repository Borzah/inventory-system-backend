package com.demo.inventory.item.controller;

import com.demo.inventory.item.dto.CategoryDto;
import com.demo.inventory.item.dto.FolderDto;
import com.demo.inventory.item.model.Category;
import com.demo.inventory.item.model.Folder;
import com.demo.inventory.item.repository.FolderRepository;
import com.demo.inventory.item.service.FolderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("folders")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class FolderController {

    private final FolderService folderService;
    private final FolderRepository folderRepository;

    @PostMapping
    public FolderDto addFolder(@RequestBody FolderDto folderDto) {
        return folderService.addFolder(folderDto);
    }

    @GetMapping
    public List<FolderDto> getAllFolders() {
        return folderService.getAllFolders();
    }

    @DeleteMapping("{folderId}")
    public void deleteFolder(@PathVariable Long folderId) {
        folderService.deleteFolder(folderId);
    }

    @GetMapping("greater/user/{userId}/folder/{folderId}")
    public List<Folder> test(@PathVariable Long userId, @PathVariable Long folderId) {
        return folderRepository.findAllByUserIdAndFolderIdIsLessThan(userId, folderId);
    }
}
