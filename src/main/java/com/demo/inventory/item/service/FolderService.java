package com.demo.inventory.item.service;

import com.demo.inventory.item.dto.FolderDto;
import com.demo.inventory.item.model.Folder;
import com.demo.inventory.item.repository.FolderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;

    public FolderDto addFolder(FolderDto folderDto) {
        Folder folder = new Folder();
        String folderName = folderDto.getFolderName();
        folder.setFolderName(folderName);
        folder.setUserId(folderDto.getUserId());
        Long parentId = folderDto.getParentId();
        if (parentId != null) {
            folder.setParentId(folderDto.getParentId());
        }
        return convertFolder(folderRepository.save(folder));
    }

    public List<FolderDto> getAllFolders() {
        return folderRepository.findAll().stream()
                .map(this::convertFolder)
                .collect(Collectors.toList());
    }

    public void deleteFolder(Long folderId) {
        folderRepository.deleteById(folderId);
    }

    private FolderDto convertFolder(Folder folder) {
        FolderDto folderDto = new FolderDto();
        folderDto.setFolderId(folder.getFolderId());
        folderDto.setFolderName(folder.getFolderName());
        folderDto.setUserId(folder.getUserId());
        folderDto.setParentId(folder.getParentId());
        return folderDto;
    }
}
