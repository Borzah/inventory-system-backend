package com.demo.inventory.item.repository;

import com.demo.inventory.item.model.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {

    List<Folder> findAllByUserIdAndParentId(Long userId, Long parentId);

    List<Folder> findAllByFolderNameAndUserIdAndParentId(String folderName, Long userId, Long parentId);

    List<Folder> findAllByUserId(Long userId);

    Folder findByFolderId(Long folderId);

    Optional<Folder> findByFolderIdAndUserId(Long folderId, Long userId);

    List<Folder> findAllByUserIdAndFolderIdIsLessThan(Long userId, Long folderId);
}
