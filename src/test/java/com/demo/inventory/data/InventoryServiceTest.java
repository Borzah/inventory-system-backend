package com.demo.inventory.data;

import com.demo.inventory.data.dto.FolderResponse;
import com.demo.inventory.data.dto.ItemResponse;
import com.demo.inventory.data.service.InventoryService;
import com.demo.inventory.data.utils.InventoryUtils;
import com.demo.inventory.item.dto.FolderDto;
import com.demo.inventory.item.model.Folder;
import com.demo.inventory.item.model.Item;
import com.demo.inventory.item.repository.FolderRepository;
import com.demo.inventory.item.repository.ItemRepository;
import com.demo.inventory.item.service.FolderService;
import com.demo.inventory.security.AuthChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
@SpringBootTest
public class InventoryServiceTest {

//    @MockBean
//    private ItemRepository itemRepository;
//    @MockBean
//    private FolderRepository folderRepository;
//    @MockBean
//    private FolderService folderService;
//    @MockBean
//    private InventoryUtils inventoryUtils;
//    @MockBean
//    private AuthChecker authChecker;
//
//    private InventoryService inventoryService;
//
//    @BeforeEach
//    void setUp() {
//        inventoryService = new InventoryService(
//                itemRepository,
//                folderRepository,
//                folderService,
//                inventoryUtils,
//                authChecker
//        );
//    }
//
//    @Test
//    void shouldGenerateValidFolderResponse() {
//        Date date = new Date();
//        Folder folder1 = new Folder(1L, "test1", null, 1L);
//        Folder folder2 = new Folder(2L, "test2", 1L, 1L);
//        Folder folder3 = new Folder(3L, "test3", 1L, 1L);
//        FolderDto folderDto2 = FolderDto.builder().folderId(2L).folderName("test2").parentId(1L).userId(1L).build();
//        FolderDto folderDto3 = FolderDto.builder().folderId(3L).folderName("test3").parentId(1L).userId(1L).build();
//        Item item = Item.builder().itemId(1L).itemName("test").dateAdded(date).build();
//        ItemResponse itemResponse = ItemResponse.builder().itemId(1L).itemName("test").dateAdded(date).imageBytes(new byte[]{}).build();
//
//        when(inventoryUtils.createItemResponse(item)).thenReturn(
//                itemResponse
//        );
//
//        when(folderRepository.findAllByUserIdAndParentId(1L, 1L)).thenReturn(
//                List.of(folder2, folder3)
//        );
//
//        when(folderRepository.findByFolderId(1L)).thenReturn(folder1);
//        when(folderRepository.findAllByUserIdAndFolderIdIsLessThan(1L, 1L))
//                .thenReturn(List.of());
//
//        when(inventoryUtils.getFolderPathName(List.of(), folder1))
//                .thenReturn("My-Items/test1/");
//
//        FolderResponse expected = FolderResponse.builder()
//                .currentFolderId(1L)
//                .parentFolderId(null)
//                .currentFolderName("test1")
//                .currentFolderPathName("My-Items/test1/")
//                .folders(List.of(folderDto2, folderDto3))
//                .items(List.of(itemResponse)).build();
//
//        FolderResponse actual = inventoryService.getContentBySection(1L, 1L, "");
//
//        assertThat(actual).isEqualTo(expected);
//    }
}
