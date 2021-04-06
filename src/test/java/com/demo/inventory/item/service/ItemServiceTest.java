package com.demo.inventory.item.service;

import com.demo.inventory.configuration.setup.StartDataRunner;
import com.demo.inventory.item.dto.ItemDto;
import com.demo.inventory.item.mapper.ItemMapper;
import com.demo.inventory.item.model.Item;
import com.demo.inventory.item.repository.ItemRepository;
import com.demo.inventory.item.utils.ItemUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ItemServiceTest {

    // Added to avoid errors
    @MockBean
    private StartDataRunner startDataRunner;

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private ItemUtils itemUtils;

    @MockBean
    private ItemMapper mapper;

    private ItemService itemService;

    @BeforeEach
    void setUp() {
        itemService = new ItemService(itemRepository, itemUtils, mapper);
    }

    @Test
    void shouldGetExistingItemById() {
        Item testItem = Item.builder().itemId(1L).itemName("test").build();
        ItemDto testItemDto = ItemDto.builder().itemId(1L).itemName("test").build();

        when(itemRepository.findByItemIdAndUserId(1L, 1L))
                .thenReturn(Optional.of(
                        testItem));

        when(mapper.fromItem(testItem)).thenReturn(testItemDto);

        ItemDto result = itemService.getItem(1L, 1L);

        assertThat(result.getItemName()).isEqualTo("test");
    }
}
