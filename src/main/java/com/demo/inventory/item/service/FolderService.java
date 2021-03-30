package com.demo.inventory.item.service;

import com.demo.inventory.exception.FolderException;
import com.demo.inventory.exception.RequestedObjectNotFoundException;
import com.demo.inventory.item.utils.ItemUtils;
import com.demo.inventory.item.dto.FolderDto;
import com.demo.inventory.item.model.Folder;
import com.demo.inventory.item.repository.FolderRepository;
import com.demo.inventory.security.AuthChecker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;
    private final AuthChecker authChecker;
    private final ItemUtils itemUtils;

    public FolderDto addFolder(FolderDto folderDto, String authToken) {
        Long userId = folderDto.getUserId();
        authChecker.checkUserAttachingTheirInfo(userId, authToken);
        itemUtils.checkNamingRegex(List.of(folderDto.getFolderName()));
        if (Optional.ofNullable(folderDto.getParentId()).isPresent()) {
            itemUtils.checkUserAddingItemOrFolderIntoTheirFolder(folderDto.getParentId(), userId);
        }
        if (!folderRepository.findAllByFolderNameAndUserIdAndParentId(folderDto.getFolderName(), userId, folderDto.getParentId()).isEmpty()) {
            throw new FolderException("Folder with such name already exists in this section");
        }
        return convertFolder(folderRepository.save(createFolderFromFolderDto(folderDto)));
    }

    public List<FolderDto> getAllFolders() {
        return folderRepository.findAll().stream()
                .map(this::convertFolder)
                .collect(Collectors.toList());
    }

    public void deleteFolder(Long folderId, String authToken) {
        Optional<Folder> folderOptional = folderRepository.findById(folderId);
        if (folderOptional.isEmpty()) {
            throw new RequestedObjectNotFoundException(
                    String.format("Folder with id [%d] does not exist", folderId));
        }
        Long userId = folderOptional.get().getUserId();
        authChecker.checkUserAttachingTheirInfo(userId, authToken);
        folderRepository.deleteById(folderId);
    }

    public FolderDto convertFolder(Folder folder) {
        FolderDto folderDto = new FolderDto();
        folderDto.setFolderId(folder.getFolderId());
        folderDto.setFolderName(folder.getFolderName());
        folderDto.setUserId(folder.getUserId());
        folderDto.setParentId(folder.getParentId());
        return folderDto;
    }

    private Folder createFolderFromFolderDto(FolderDto folderDto) {
        Folder folder = new Folder();
        folder.setFolderName(folderDto.getFolderName());
        folder.setUserId(folderDto.getUserId());
        folder.setParentId(folderDto.getParentId());
        return folder;
    }
}
