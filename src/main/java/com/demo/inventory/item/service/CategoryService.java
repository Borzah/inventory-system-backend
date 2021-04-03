package com.demo.inventory.item.service;

import com.demo.inventory.exception.ItemException;
import com.demo.inventory.item.dto.CategoryDto;
import com.demo.inventory.item.model.Category;
import com.demo.inventory.item.repository.CategoryRepository;
import com.demo.inventory.item.utils.ItemUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ItemUtils itemUtils;

    public List<CategoryDto> getAllCategoriesByUserId(Long userId) {
        return categoryRepository.findAllByUserId(userId).stream()
                .map(this::convertCategory)
                .collect(Collectors.toList());
    }

    public CategoryDto addCategory(CategoryDto categoryDto, Long userId) {
        itemUtils.checkNamingRegex(categoryDto.getCategoryName());

        if (categoryRepository.findByUserIdAndCategoryName(userId, categoryDto.getCategoryName()).isPresent()) {
            throw new ItemException("Category with such name is already present");
        }

        Category category = new Category(userId, categoryDto.getCategoryName());

        return convertCategory(categoryRepository.save(category));
    }

    private CategoryDto convertCategory(Category category) {
        return CategoryDto.builder()
                .categoryId(category.getCategoryId())
                .userId(category.getUserId())
                .categoryName(category.getCategoryName()).build();
    }
}
