package com.demo.inventory.item.repository;

import com.demo.inventory.item.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findByItemId(Long itemId);

    List<Item> findAllByUserIdAndFolderId(Long userId, Long folderId);

    List<Item> findAllByUserId(Long userId);

    List<Item> findAllByCategoryId(Long categoryId);

    List<Item> findAllByUserIdAndCategoryIdNotNull(Long userId);

    List<Item> findAllByUserIdAndSerialNumberNotNull(Long userId);

    List<Item> findAllByUserIdAndDescriptionNotNull(Long userId);

    List<Item> findAllByUserIdAndItemPriceNotNull(Long userId);

    List<Item> findAllByItemNameAndFolderIdAndUserId(String itemName, Long folderId, Long userId);

    // custom queries
    @Query("SELECT i FROM Item i INNER JOIN Category c ON i.categoryId=c.categoryId AND i.userId = :userId AND Lower(c.categoryName) LIKE %:search%")
    List<Item> searchForItemsByCategory(Long userId, String search);

    @Query("SELECT i FROM Item i WHERE i.userId = :userId AND Lower(i.serialNumber) LIKE %:search%")
    List<Item> searchForSerialNumberContainingAndUserId(String search, Long userId);

    @Query("SELECT i FROM Item i WHERE i.userId = :userId AND Lower(i.description) LIKE %:search%")
    List<Item> searchForDescriptionContainingAndUserId(String search, Long userId);

    @Query("SELECT i FROM Item i WHERE i.userId = :userId AND Lower(i.itemName) LIKE %:search%")
    List<Item> searchForItemNameContainingAndUserId(String search, Long userId);

    @Query("SELECT i FROM Item i WHERE i.userId = :userId AND CONCAT(i.itemPrice, '') LIKE %:search%")
    List<Item> searchForItemPriceAndUserId(String search, Long userId);

    @Query("SELECT DISTINCT i FROM Item i, Category c " +
        "WHERE i.userId=:userId AND " +
        "(Lower(i.itemName) LIKE %:search% " +
        "OR Lower(i.serialNumber) LIKE %:search% " +
        "OR Lower(i.description) LIKE %:search% " +
        "OR CONCAT(i.itemPrice, '') LIKE %:search%) " +
        "OR c.categoryId = i.categoryId AND Lower(c.categoryName) LIKE %:search%")
    List<Item> searchForItemsByALlFields(Long userId, String search);
}
