package com.demo.inventory.item.repository;

import com.demo.inventory.item.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByCategoryId(Long categoryId);

    List<Category> findAllByUserId(Long userId);
}
