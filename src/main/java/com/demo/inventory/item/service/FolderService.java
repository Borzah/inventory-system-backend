package com.demo.inventory.item.service;

import com.demo.inventory.exception.FolderException;
import com.demo.inventory.exception.RequestedObjectNotFoundException;
import com.demo.inventory.item.mapper.FolderMapper;
import com.demo.inventory.item.utils.ItemUtils;
import com.demo.inventory.item.dto.FolderDto;
import com.demo.inventory.item.model.Folder;
import com.demo.inventory.item.repository.FolderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;
    private final ItemUtils itemUtils;
    private final FolderMapper mapper;

    public FolderDto addFolder(FolderDto folderDto, Long userId) {
        itemUtils.checkNamingRegex(folderDto.getFolderName());

        if (Optional.ofNullable(folderDto.getParentId()).isPresent()) {
            itemUtils.checkUserAddingItemOrFolderIntoTheirFolder(folderDto.getParentId(), userId);
        }
        if (!folderRepository.findAllByFolderNameAndUserIdAndParentId(
                folderDto.getFolderName(), userId, folderDto.getParentId()).isEmpty()) {
            throw new FolderException("Folder with such name already exists in this section");
        }

        folderDto.setUserId(userId);
        return mapper.fromFolder(folderRepository.save(mapper.toFolder(folderDto)));
    }

    public List<FolderDto> getAllUserFolders(Long userId) {
        return folderRepository.findAllByUserId(userId).stream()
                .map(mapper::fromFolder)
                .collect(Collectors.toList());
    }

    public void deleteFolder(Long folderId, Long userId) {
        Optional<Folder> folderOptional = folderRepository.findByFolderIdAndUserId(folderId, userId);

        if (folderOptional.isEmpty()) {
            throw new RequestedObjectNotFoundException(
                    String.format("Folder with id [%d] and user id [%d] does not exist", folderId, userId));
        }

        folderRepository.deleteById(folderId);
    }
}
