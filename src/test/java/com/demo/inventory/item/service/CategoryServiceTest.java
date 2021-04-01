package com.demo.inventory.item.service;

import com.demo.inventory.configuration.StartDataUserConfig;
import com.demo.inventory.exception.ItemException;
import com.demo.inventory.item.dto.CategoryDto;
import com.demo.inventory.item.model.Category;
import com.demo.inventory.item.repository.CategoryRepository;
import com.demo.inventory.item.utils.ItemUtils;
import com.demo.inventory.security.AuthChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CategoryServiceTest {

    @MockBean // Added to avoid conflicts
    private StartDataUserConfig startDataUserConfig;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private AuthChecker authChecker;

    @MockBean
    private ItemUtils itemUtils;

    private CategoryService categoryService;
    private CategoryDto categoryDto;
    private Category category;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryService(categoryRepository, authChecker, itemUtils);
        categoryDto = new CategoryDto(1L, "test");
        category = new Category();
    }

    @Test
    void shouldNotAddCategory() {
        when(categoryRepository.findAllByUserIdAndCategoryName(1L,"test"))
                .thenReturn(List.of(category));

        assertThatThrownBy(() -> categoryService.addCategory(categoryDto, ""))
                .isInstanceOf(ItemException.class)
                .hasMessageContaining("Category with such name is already present");
    }

}
