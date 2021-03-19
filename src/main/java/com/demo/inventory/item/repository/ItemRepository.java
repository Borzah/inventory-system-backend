package com.demo.inventory.item.repository;

import com.demo.inventory.item.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findByItemId(Long itemId);

    List<Item> findAllByUserIdAndFolderId(Long userId, Long folderId);

    List<Item> findAllByUserId(Long userId);
}
