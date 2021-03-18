package com.demo.inventory.item.repository;

import com.demo.inventory.item.model.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {

    List<Folder> findAllByUserIdAndParentId(Long userId, Long parentId);

    Folder findByFolderId(Long folderId);
}
