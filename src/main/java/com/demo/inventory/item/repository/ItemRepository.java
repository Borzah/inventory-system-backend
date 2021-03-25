package com.demo.inventory.item.repository;

import com.demo.inventory.item.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findByItemId(Long itemId);

    Item findByItemIdAndUserId(Long itemId, Long userId);

    List<Item> findAllByUserIdAndFolderId(Long userId, Long folderId);

    List<Item> findAllByUserId(Long userId);

    List<Item> findAllByUserIdAndCategoryIdNotNull(Long userId);

    List<Item> findAllByUserIdAndSerialNumberNotNull(Long userId);

    List<Item> findAllByUserIdAndDescriptionNotNull(Long userId);

    List<Item> findAllByUserIdAndItemPriceNotNull(Long userId);

    List<Item> findAllByItemNameAndFolderIdAndUserId(String itemName, Long folderId, Long userId);
}
