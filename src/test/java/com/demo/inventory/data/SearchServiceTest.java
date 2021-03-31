package com.demo.inventory.data;

import com.demo.inventory.data.dto.ItemNodeResponse;
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

import java.sql.Timestamp;
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

    private ItemNodeResponse testItemResponse;

    private SearchService searchService;

    @BeforeEach
    void setUp() {
        searchService = new SearchService(itemRepository, inventoryUtils, authChecker);
        testItem = new Item(1L, "test", 1L, 1L, 1L, new Timestamp(System.currentTimeMillis()), "test description", "12345", 12.99f);
        testItemResponse = new ItemNodeResponse(1L, "test");
    }

    @Test
    void searchForItemsTest() {
        when(itemRepository.searchForItemNameContainingAndUserId("te", 1L)).thenReturn(List.of(
                testItem
        ));
        when(inventoryUtils.createItemNodeResponse(testItem)).thenReturn(testItemResponse);
        assertThat(searchService.getAllUsersItemNodes(1L,"name", "te", ""))
                .isEqualTo(List.of(testItemResponse));
        assertThat(searchService.getAllUsersItemNodes(1L,"name", "unknown", ""))
                .isEqualTo(List.of());
    }
}
