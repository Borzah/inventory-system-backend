package com.demo.inventory.item.service;

import com.demo.inventory.exception.ItemException;
import com.demo.inventory.item.dto.CategoryDto;
import com.demo.inventory.item.mapper.CategoryMapper;
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
    private final CategoryMapper mapper;

    public List<CategoryDto> getAllCategoriesByUserId(Long userId) {
        return categoryRepository.findAllByUserId(userId).stream()
                .map(mapper::fromCategory)
                .collect(Collectors.toList());
    }

    public CategoryDto addCategory(CategoryDto categoryDto, Long userId) {
        itemUtils.checkNamingRegex(categoryDto.getCategoryName());

        if (categoryRepository.findByUserIdAndCategoryName(userId, categoryDto.getCategoryName()).isPresent()) {
            throw new ItemException("Category with such name is already present");
        }

        Category category = new Category(userId, categoryDto.getCategoryName());

        return mapper.fromCategory(categoryRepository.save(category));
    }
}
