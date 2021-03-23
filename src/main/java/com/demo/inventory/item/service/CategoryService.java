package com.demo.inventory.item.service;

import com.demo.inventory.item.dto.CategoryDto;
import com.demo.inventory.item.model.Category;
import com.demo.inventory.item.repository.CategoryRepository;
import com.demo.inventory.security.AuthChecker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final AuthChecker authChecker;

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::convertCategory)
                .collect(Collectors.toList());
    }

    public List<CategoryDto> getAllCategoriesByUserId(Long userId, String authToken) {
        authChecker.checkUserAttachingTheirInfo(userId, authToken);
        return categoryRepository.findAllByUserId(userId).stream()
                .map(this::convertCategory)
                .collect(Collectors.toList());
    }

    public CategoryDto addCategory(CategoryDto categoryDto, String authToken) {
        Long userId = categoryDto.getUserId();
        authChecker.checkUserAttachingTheirInfo(userId, authToken);
        Category category = new Category(userId, categoryDto.getCategoryName());
        return convertCategory(categoryRepository.save(category));
    }

    private CategoryDto convertCategory(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryId(category.getCategoryId());
        categoryDto.setUserId(category.getUserId());
        categoryDto.setCategoryName(category.getCategoryName());
        return categoryDto;
    }
}
