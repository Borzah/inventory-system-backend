//package com.demo.inventory.admin;
//
//import com.demo.inventory.admin.dto.UserStatisticsResponse;
//import com.demo.inventory.admin.service.StatisticsService;
//import com.demo.inventory.configuration.StartDataUserConfig;
//import com.demo.inventory.item.model.Folder;
//import com.demo.inventory.item.model.Item;
//import com.demo.inventory.item.repository.FolderRepository;
//import com.demo.inventory.item.repository.ItemRepository;
//import com.demo.inventory.security.DbRole;
//import com.demo.inventory.user.model.User;
//import com.demo.inventory.user.repository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.mockito.Mockito.when;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//public class StatisticsServiceTest {
//
//    @MockBean // Added to avoid conflicts
//    private StartDataUserConfig startDataUserConfig;
//
//    @MockBean
//    private UserRepository userRepository;
//
//    @MockBean
//    private FolderRepository folderRepository;
//
//    @MockBean
//    private ItemRepository itemRepository;
//
//    private StatisticsService statisticsService;
//
//    @BeforeEach
//    void setUp() {
//        statisticsService = new StatisticsService(userRepository, folderRepository, itemRepository);
//    }
//
//    @Test
//    void shouldGetStatistics() {
//        Timestamp registeredAt = new Timestamp(System.currentTimeMillis());
//        User admin = new User("test", "test", DbRole.ADMIN, registeredAt);
//        admin.setUserId(0L);
//        User user = new User("test", "test", DbRole.USER, registeredAt);
//        user.setUserId(1L);
//        Timestamp resultDate = new Timestamp(System.currentTimeMillis());
//        List<Item> items = new ArrayList<>();
//        items.add(Item.builder().dateAdded(resultDate).build());
//        List<Folder> folders = new ArrayList<>();
//        folders.add(new Folder());
//
//        when(userRepository.findAll())
//                .thenReturn(List.of(
//                        admin,
//                        user
//                ));
//        when(itemRepository.findAllByUserId(1L)).thenReturn(items);
//        when(folderRepository.findAllByUserId(1L)).thenReturn(folders);
//
//        List<UserStatisticsResponse> expectedResult = List.of(
//                UserStatisticsResponse.builder()
//                        .username("test")
//                        .lastItemAddedAt(resultDate)
//                        .numOfFolders(1)
//                        .numOfItems(1)
//                        .registeredAt(registeredAt).build()
//        );
//
//        List<UserStatisticsResponse> actualResult = statisticsService.getUserStatistics();
//
//        assertThat(actualResult.get(0).getUsername())
//                .isEqualTo(expectedResult.get(0).getUsername());
//        assertThat(actualResult.get(0).getLastItemAddedAt())
//                .isEqualTo(expectedResult.get(0).getLastItemAddedAt());
//        assertThat(actualResult.get(0).getNumOfFolders())
//                .isEqualTo(expectedResult.get(0).getNumOfFolders());
//        assertThat(actualResult.get(0).getNumOfItems())
//                .isEqualTo(expectedResult.get(0).getNumOfItems());
//        assertThat(actualResult.get(0).getRegisteredAt())
//                .isEqualTo(expectedResult.get(0).getRegisteredAt());
//    }
//}
