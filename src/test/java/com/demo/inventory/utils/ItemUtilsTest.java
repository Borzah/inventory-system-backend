package com.demo.inventory.utils;

import com.demo.inventory.configuration.StartDataUserConfig;
import com.demo.inventory.exception.FolderException;
import com.demo.inventory.exception.ItemException;
import com.demo.inventory.item.model.Folder;
import com.demo.inventory.item.repository.FolderRepository;
import com.demo.inventory.item.utils.ItemUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;


@SpringBootTest
public class ItemUtilsTest {

    @MockBean
    private StartDataUserConfig startDataUserConfig;

    @MockBean
    private FolderRepository folderRepository;

    private ItemUtils itemUtils;

    @BeforeEach
    void setUp() {
        itemUtils = new ItemUtils(folderRepository);
    }

    @Test
    void itShouldThrowExceptionUserAddingItemOrFolderIntoOtherFolder() {
        when(folderRepository.findAllByFolderIdAndUserId(1L, 2L)).thenReturn(new ArrayList<>());

        assertThatThrownBy(() -> itemUtils.checkUserAddingItemOrFolderIntoTheirFolder(1L, 2L))
                .isInstanceOf(FolderException.class)
                .hasMessageContaining("It is possible to add content only into your folder");
    }

    @Test()
    void itShouldPassUserAddingItemOrFolderIntoTheirFolder() {
        when(folderRepository.findAllByFolderIdAndUserId(1L, 2L))
                .thenReturn(List.of(new Folder("test", 0L, 2L)));

        assertDoesNotThrow(() ->
                itemUtils.checkUserAddingItemOrFolderIntoTheirFolder(1L, 2L));
    }

    @Test
    void shouldNValidateNameTest() {
        assertDoesNotThrow(() -> itemUtils.checkNamingRegex(List.of("test")));
    }

    @Test
    void shouldNotValidateNameTest() {
        assertThatThrownBy(() -> itemUtils.checkNamingRegex(List.of("te_st")))
                .isInstanceOf(ItemException.class)
                .hasMessageContaining("Attribute name cannot contain '_' sign");
    }
}
