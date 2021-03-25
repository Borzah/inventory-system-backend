package com.demo.inventory.data;

import com.demo.inventory.data.dto.ItemResponse;
import com.demo.inventory.data.service.SearchService;
import com.demo.inventory.data.utils.InventoryUtils;
import com.demo.inventory.item.model.Item;
import com.demo.inventory.item.repository.ItemRepository;
import com.demo.inventory.security.AuthChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SearchServiceTest {

    @MockBean
    private  ItemRepository itemRepository;

    @MockBean
    private  InventoryUtils inventoryUtils;

    @MockBean
    private  AuthChecker authChecker;

    private Item testItem;

    private ItemResponse testItemResponse;

    private SearchService searchService;

    @BeforeEach
    void setUp() {
        searchService = new SearchService(itemRepository, inventoryUtils, authChecker);
        testItem = new Item(1L, "test", 1L, 1L, 1L, new Date(), "test description", "12345", 12.99f);
        testItemResponse = new ItemResponse(1L, "test", 1L, "testFolder", 1L, "categoryTest", new Date(), "descriptionTest", "12345", 12.99f, new byte[]{});
    }

    @Test
    void searchForItemsTest() {
        when(itemRepository.findAllByUserIdAndCategoryIdNotNull(1L)).thenReturn(List.of(
                testItem
        ));
        when(inventoryUtils.createItemResponse(testItem)).thenReturn(testItemResponse);
        assertThat(searchService.getAllUsersItemResponses(1L,"category", "cat", ""))
                .isEqualTo(List.of(testItemResponse));
        assertThat(searchService.getAllUsersItemResponses(1L,"category", "unknown", ""))
                .isEqualTo(List.of());
    }
}
