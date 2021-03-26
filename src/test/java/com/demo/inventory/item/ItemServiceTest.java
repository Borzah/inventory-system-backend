package com.demo.inventory.item;

import com.demo.inventory.exception.FolderException;
import com.demo.inventory.exception.ItemException;
import com.demo.inventory.item.dto.ItemDto;
import com.demo.inventory.item.model.Item;
import com.demo.inventory.item.repository.ItemRepository;
import com.demo.inventory.item.service.ItemService;
import com.demo.inventory.item.utils.ItemUtils;
import com.demo.inventory.security.AuthChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;


@SpringBootTest
public class ItemServiceTest {

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private AuthChecker authChecker;

    @MockBean
    private ItemUtils itemUtils;


    private ItemService itemService;
    private ItemDto itemDto;
    private Item item;

    @BeforeEach
    void setUp() {
        itemService = new ItemService(itemRepository, authChecker, itemUtils);
        itemDto = ItemDto.builder().itemName("test item").folderId(1L).userId(1L).build();
        item = Item.builder().itemId(1L).itemName("test item").folderId(1L).userId(1L).build();
    }

    @Test
    void itShouldNotSaveNewItem() {
        when(itemRepository.findAllByItemNameAndFolderIdAndUserId("test item", 1L, 1L))
                .thenReturn(List.of(item));
        assertThatThrownBy(() -> itemService.addItem(itemDto, "test"))
                .isInstanceOf(ItemException.class)
                .hasMessageContaining("Item with such name is already in that folder");
    }

}
