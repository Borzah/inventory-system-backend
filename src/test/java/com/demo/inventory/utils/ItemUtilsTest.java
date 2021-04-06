package com.demo.inventory.utils;

import com.demo.inventory.configuration.setup.StartDataRunner;
import com.demo.inventory.exception.FolderException;
import com.demo.inventory.exception.ItemException;
import com.demo.inventory.item.model.Category;
import com.demo.inventory.item.model.Folder;
import com.demo.inventory.item.repository.CategoryRepository;
import com.demo.inventory.item.repository.FolderRepository;
import com.demo.inventory.item.utils.ItemUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;


@SpringBootTest
public class ItemUtilsTest {

    // Added to avoid errors
    @MockBean
    private StartDataRunner startDataRunner;

    @MockBean
    private FolderRepository folderRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    private ItemUtils itemUtils;

    @BeforeEach
    void setUp() {
        itemUtils = new ItemUtils(folderRepository, categoryRepository);
    }

    @Test
    void itShouldThrowExceptionUserAddingItemOrFolderIntoOtherFolder() {
        when(folderRepository.findByFolderIdAndUserId(1L, 1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> itemUtils.checkUserAddingItemOrFolderIntoTheirFolder(1L, 1L))
                .isInstanceOf(FolderException.class)
                .hasMessageContaining("Folder into which you are trying to add does not exist.");
    }

    @Test
    void itShouldThrowExceptionUserAddingItemToOtherUserCategory() {
        when(categoryRepository.findByCategoryIdAndUserId(1L, 1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> itemUtils.checkUserIsAddingItemToTheirCategory(1L, 1L))
                .isInstanceOf(ItemException.class)
                .hasMessageContaining("Category with id [1] and user id [1] to which you are trying to add item does not exist");
    }

    @Test()
    void itShouldPassUserAddingItemOrFolderIntoTheirFolder() {
        when(folderRepository.findByFolderIdAndUserId(1L, 1L))
                .thenReturn(Optional.of(Folder.builder().userId(1L).build()));

        assertDoesNotThrow(() ->
                itemUtils.checkUserAddingItemOrFolderIntoTheirFolder(1L, 1L));
    }

    @Test
    void itShouldPassUserAddingItemToTheirCategory() {
        when(categoryRepository.findByCategoryIdAndUserId(1L, 1L)).
                thenReturn(Optional.of(Category.builder().userId(1L).build()));

        assertDoesNotThrow(() ->
                itemUtils.checkUserIsAddingItemToTheirCategory(1L, 1L));
    }

    @Test
    void shouldNValidateNameTest() {
        assertDoesNotThrow(() -> itemUtils.checkNamingRegex("test"));
    }

    @Test
    void shouldNotValidateNameTest() {
        assertThatThrownBy(() -> itemUtils.checkNamingRegex("te_st"))
                .isInstanceOf(ItemException.class)
                .hasMessageContaining("Attribute name cannot contain '_' sign");
    }
}
