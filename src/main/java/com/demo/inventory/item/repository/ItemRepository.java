package com.demo.inventory.item.repository;

import com.demo.inventory.item.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findByItemIdAndUserId(Long itemId, Long userId);

    List<Item> findAllByUserIdAndFolderId(Long userId, Long folderId);

    List<Item> findAllByUserId(Long userId);

    List<Item> findAllByCategoryId(Long categoryId);

    List<Item> findAllByUserIdAndCategoryIdNotNull(Long userId);

    List<Item> findAllByUserIdAndSerialNumberNotNull(Long userId);

    List<Item> findAllByUserIdAndDescriptionNotNull(Long userId);

    List<Item> findAllByUserIdAndItemPriceNotNull(Long userId);

    List<Item> findAllByUserIdAndFolderIdNotNull(Long userId);

    // custom queries

    @Query("SELECT i FROM Item i INNER JOIN Category c ON i.categoryId=c.categoryId AND i.userId = :userId AND Lower(c.categoryName) LIKE %:search%")
    List<Item> searchForItemsByCategory(Long userId, String search);

    @Query("SELECT i FROM Item i INNER JOIN Folder f ON i.folderId=f.folderId AND i.userId = :userId AND Lower(f.folderName) LIKE %:search%")
    List<Item> searchForItemsByFolder(Long userId, String search);

    @Query("SELECT i FROM Item i WHERE i.userId = :userId AND Lower(i.serialNumber) LIKE %:search%")
    List<Item> searchForSerialNumberContainingAndUserId(String search, Long userId);

    @Query("SELECT i FROM Item i WHERE i.userId = :userId AND Lower(i.description) LIKE %:search%")
    List<Item> searchForDescriptionContainingAndUserId(String search, Long userId);

    @Query("SELECT i FROM Item i WHERE i.userId = :userId AND Lower(i.itemName) LIKE %:search%")
    List<Item> searchForItemNameContainingAndUserId(String search, Long userId);

    @Query("SELECT i FROM Item i WHERE i.userId = :userId AND CONCAT(i.itemPrice, '') LIKE %:search%")
    List<Item> searchForItemPriceAndUserId(String search, Long userId);
}
