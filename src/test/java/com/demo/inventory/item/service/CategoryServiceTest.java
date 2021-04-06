package com.demo.inventory.item.service;

import com.demo.inventory.configuration.setup.StartDataRunner;
import com.demo.inventory.exception.ItemException;
import com.demo.inventory.item.dto.CategoryDto;
import com.demo.inventory.item.mapper.CategoryMapper;
import com.demo.inventory.item.model.Category;
import com.demo.inventory.item.repository.CategoryRepository;
import com.demo.inventory.item.utils.ItemUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CategoryServiceTest {

    // Added to avoid errors
    @MockBean
    private StartDataRunner startDataRunner;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private ItemUtils itemUtils;

    @MockBean
    private CategoryMapper mapper;

    private CategoryService categoryService;
    private CategoryDto categoryDto;
    private Category category;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryService(categoryRepository, itemUtils, mapper);
        categoryDto = new CategoryDto(1L, "test");
        category = new Category();
    }

    @Test
    void shouldNotAddCategory() {
        when(categoryRepository.findByUserIdAndCategoryName(1L,"test"))
                .thenReturn(Optional.of(category));

        assertThatThrownBy(() -> categoryService.addCategory(categoryDto, 1L))
                .isInstanceOf(ItemException.class)
                .hasMessageContaining("Category with such name is already present");
    }

}
