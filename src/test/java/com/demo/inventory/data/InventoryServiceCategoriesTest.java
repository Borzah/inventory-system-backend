package com.demo.inventory.data;

import com.demo.inventory.configuration.setup.StartDataUserConfig;
import com.demo.inventory.data.dto.ItemNodeResponse;
import com.demo.inventory.data.mapper.FolderResponseMapper;
import com.demo.inventory.data.service.InventoryService;
import com.demo.inventory.data.utils.InventoryUtils;
import com.demo.inventory.item.mapper.FolderMapper;
import com.demo.inventory.item.model.Category;
import com.demo.inventory.item.model.Item;
import com.demo.inventory.item.repository.CategoryRepository;
import com.demo.inventory.item.repository.FolderRepository;
import com.demo.inventory.item.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class InventoryServiceCategoriesTest {

    // Added to avoid conflicts with @PostConstruct
    @MockBean
    private StartDataUserConfig startDataUserConfig;

    @MockBean
    private ItemRepository itemRepository;
    @MockBean
    private FolderRepository folderRepository;
    @MockBean
    private CategoryRepository categoryRepository;
    @MockBean
    private InventoryUtils inventoryUtils;
    @MockBean
    private FolderMapper folderMapper;
    @MockBean
    private FolderResponseMapper folderResponseMapper;

    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        inventoryService = new InventoryService(
                itemRepository,
                folderRepository,
                categoryRepository,
                inventoryUtils,
                folderMapper,
                folderResponseMapper
        );
    }

    @Test
    void ShouldReturnValidItemsByCategories() {
        Timestamp date = new Timestamp(System.currentTimeMillis());

        Category category1 = Category.builder().categoryId(1L).userId(1L).categoryName("test1").build();
        Category category2 = Category.builder().categoryId(2L).userId(1L).categoryName("test2").build();

        Item item1 = Item.builder().itemId(1L).itemName("testItem1").dateAdded(date).build();
        Item item2 = Item.builder().itemId(2L).itemName("testItem2").dateAdded(date).build();

        ItemNodeResponse itemNode1 = new ItemNodeResponse(1L, "testItem1");
        ItemNodeResponse itemNode2 = new ItemNodeResponse(2L, "testItem2");

        when(categoryRepository.findAllByUserId(1L)).thenReturn(
                List.of(category1, category2)
        );

        when(itemRepository.findAllByCategoryId(1L)).thenReturn(List.of(item1));
        when(itemRepository.findAllByCategoryId(2L)).thenReturn(List.of(item2));

        when(inventoryUtils.createItemNodeResponse(item1)).thenReturn(itemNode1);
        when(inventoryUtils.createItemNodeResponse(item2)).thenReturn(itemNode2);

        Map<String, List<ItemNodeResponse>> expected = new HashMap<>();
        expected.put("test1", List.of(itemNode1));
        expected.put("test2", List.of(itemNode2));

        Map<String, List<ItemNodeResponse>> actual = inventoryService.getItemsByCategory(1L);

        assertThat(actual).isEqualTo(expected);
    }
}
