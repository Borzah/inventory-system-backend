//package com.demo.inventory.item.service;
//
//import com.demo.inventory.configuration.StartDataUserConfig;
//import com.demo.inventory.item.dto.ItemDto;
//import com.demo.inventory.item.model.Item;
//import com.demo.inventory.item.repository.ItemRepository;
//import com.demo.inventory.item.utils.ItemUtils;
//import com.demo.inventory.security.AuthChecker;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import java.util.Optional;
//
//import static org.mockito.Mockito.when;
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//public class ItemServiceTest {
//
//    @MockBean // Added to avoid conflicts
//    private StartDataUserConfig startDataUserConfig;
//
//    @MockBean
//    private ItemRepository itemRepository;
//
//    @MockBean
//    private AuthChecker authChecker;
//
//    @MockBean
//    private ItemUtils itemUtils;
//
//    private ItemService itemService;
//
//    @BeforeEach
//    void setUp() {
//        itemService = new ItemService(itemRepository, authChecker, itemUtils);
//    }
//
//    @Test
//    void shouldGetExistingItemById() {
//        Item testItem = Item.builder().itemId(1L).itemName("test").build();
//
//        when(itemRepository.findById(1L))
//                .thenReturn(Optional.of(
//                        testItem));
//
//        ItemDto result = itemService.getItem(1L, "");
//
//        assertThat(result.getItemName()).isEqualTo("test");
//    }
//}
