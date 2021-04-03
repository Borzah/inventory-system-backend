//package com.demo.inventory.utils;
//
//import com.demo.inventory.configuration.StartDataUserConfig;
//import com.demo.inventory.exception.AuthorizationException;
//import com.demo.inventory.exception.FolderException;
//import com.demo.inventory.exception.ItemException;
//import com.demo.inventory.item.model.Category;
//import com.demo.inventory.item.model.Folder;
//import com.demo.inventory.item.repository.CategoryRepository;
//import com.demo.inventory.item.repository.FolderRepository;
//import com.demo.inventory.item.utils.ItemUtils;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//import static org.mockito.Mockito.when;
//
//
//@SpringBootTest
//public class ItemUtilsTest {
//
//    @MockBean // Added to avoid conflicts
//    private StartDataUserConfig startDataUserConfig;
//
//    @MockBean
//    private FolderRepository folderRepository;
//
//    @MockBean
//    private CategoryRepository categoryRepository;
//
//    private ItemUtils itemUtils;
//
//    @BeforeEach
//    void setUp() {
//        itemUtils = new ItemUtils(folderRepository, categoryRepository);
//    }
//
//    @Test
//    void itShouldThrowExceptionUserAddingItemOrFolderIntoOtherFolder() {
//        when(folderRepository.findById(1L)).thenReturn(Optional.of(Folder.builder().userId(1L).build()));
//
//        assertThatThrownBy(() -> itemUtils.checkUserAddingItemOrFolderIntoTheirFolder(1L, 2L))
//                .isInstanceOf(AuthorizationException.class)
//                .hasMessageContaining("It is possible to add content only into your folder");
//    }
//
//    @Test
//    void itShouldThrowExceptionUserAddingItemToOtherUserCategory() {
//        when(categoryRepository.findById(1L)).thenReturn(Optional.of(Category.builder().userId(1L).build()));
//
//        assertThatThrownBy(() -> itemUtils.checkUserIsAddingItemToTheirCategory(1L, 2L))
//                .isInstanceOf(AuthorizationException.class)
//                .hasMessageContaining("It is possible to add item only to your category");
//    }
//
//    @Test()
//    void itShouldPassUserAddingItemOrFolderIntoTheirFolder() {
//        when(folderRepository.findById(1L))
//                .thenReturn(Optional.of(Folder.builder().userId(2L).build()));
//
//        assertDoesNotThrow(() ->
//                itemUtils.checkUserAddingItemOrFolderIntoTheirFolder(1L, 2L));
//    }
//
//    @Test
//    void itShouldPassUserAddingItemToTheirCategory() {
//        when(categoryRepository.findById(1L))
//                .thenReturn(Optional.of(Category.builder().userId(2L).build()));
//
//        assertDoesNotThrow(() ->
//                itemUtils.checkUserIsAddingItemToTheirCategory(1L, 2L));
//    }
//
//    @Test
//    void shouldNValidateNameTest() {
//        assertDoesNotThrow(() -> itemUtils.checkNamingRegex(List.of("test")));
//    }
//
//    @Test
//    void shouldNotValidateNameTest() {
//        assertThatThrownBy(() -> itemUtils.checkNamingRegex(List.of("te_st")))
//                .isInstanceOf(ItemException.class)
//                .hasMessageContaining("Attribute name cannot contain '_' sign");
//    }
//}
