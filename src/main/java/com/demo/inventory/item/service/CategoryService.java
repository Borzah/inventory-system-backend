package com.demo.inventory.item.service;

import com.demo.inventory.item.dto.CategoryDto;
import com.demo.inventory.item.model.Category;
import com.demo.inventory.item.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::convertCategory)
                .collect(Collectors.toList());
    }

    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = new Category(categoryDto.getCategoryName());
        return convertCategory(categoryRepository.save(category));
    }

    private CategoryDto convertCategory(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryId(category.getCategoryId());
        categoryDto.setCategoryName(category.getCategoryName());
        return categoryDto;
    }
}
