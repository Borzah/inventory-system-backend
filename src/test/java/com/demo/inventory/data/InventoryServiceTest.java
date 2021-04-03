//package com.demo.inventory.data;
//
//import com.demo.inventory.configuration.StartDataUserConfig;
//import com.demo.inventory.data.dto.FolderResponse;
//import com.demo.inventory.data.dto.ItemNodeResponse;
//import com.demo.inventory.data.service.InventoryService;
//import com.demo.inventory.data.utils.InventoryUtils;
//import com.demo.inventory.item.dto.FolderDto;
//import com.demo.inventory.item.model.Folder;
//import com.demo.inventory.item.model.Item;
//import com.demo.inventory.item.repository.CategoryRepository;
//import com.demo.inventory.item.repository.FolderRepository;
//import com.demo.inventory.item.repository.ItemRepository;
//import com.demo.inventory.item.service.FolderService;
//import com.demo.inventory.security.AuthChecker;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import java.sql.Timestamp;
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.Mockito.when;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//public class InventoryServiceTest {
//
//    @MockBean // Added to avoid conflicts
//    private StartDataUserConfig startDataUserConfig;
//    @MockBean
//    private ItemRepository itemRepository;
//    @MockBean
//    private FolderRepository folderRepository;
//    @MockBean
//    private CategoryRepository categoryRepository;
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
//                categoryRepository,
//                folderService,
//                inventoryUtils,
//                authChecker
//        );
//    }
//
//    @Test
//    void shouldGenerateValidFolderResponse() {
//        Timestamp date = new Timestamp(System.currentTimeMillis());
//
//        Folder folder1 = new Folder(1L, "test1", null, 1L);
//        Folder folder2 = new Folder(2L, "test2", 1L, 1L);
//        Folder folder3 = new Folder(3L, "test3", 1L, 1L);
//
//        FolderDto folderDto2 = FolderDto.builder().folderId(2L).folderName("test2").parentId(1L).userId(1L).build();
//        FolderDto folderDto3 = FolderDto.builder().folderId(3L).folderName("test3").parentId(1L).userId(1L).build();
//
//        Item item = Item.builder().itemId(1L).itemName("test").dateAdded(date).build();
//        ItemNodeResponse itemNode = new ItemNodeResponse(1L, "test");
//
//        when(folderRepository.findById(1L)).thenReturn(Optional.of(folder1));
//
//        when(folderRepository.findAllByUserIdAndParentId(1L, 1L)).thenReturn(
//                List.of(folder2, folder3)
//        );
//
//        when(itemRepository.findAllByUserIdAndFolderId(1L, 1L)).thenReturn(
//                List.of(item)
//        );
//
//        when(inventoryUtils.createItemNodeResponse(item)).thenReturn(
//                itemNode
//        );
//
//        when(folderService.convertFolder(folder2)).thenReturn(folderDto2);
//        when(folderService.convertFolder(folder3)).thenReturn(folderDto3);
//
//        when(folderRepository.findByFolderId(1L)).thenReturn(folder1);
//        when(folderRepository.findAllByUserIdAndFolderIdIsLessThan(1L, 1L))
//                .thenReturn(List.of());
//
//        when(inventoryUtils.getFolderPathName(List.of(), folder1))
//                .thenReturn("My-Items / test1 /");
//
//        FolderResponse expected = FolderResponse.builder()
//                .currentFolderId(1L)
//                .parentFolderId(null)
//                .currentFolderPathName("My-Items / test1 /")
//                .folders(List.of(folderDto2, folderDto3))
//                .items(List.of(itemNode)).build();
//
//        when(inventoryUtils.createFolderResponse(
//                1L,
//                null,
//                "My-Items / test1 /",
//                List.of(folderDto2, folderDto3),
//                List.of(itemNode))).thenReturn(expected);
//
//        FolderResponse actual = inventoryService.getContentBySection(1L, 1L, "");
//
//        assertThat(actual).isEqualTo(expected);
//    }
//}
