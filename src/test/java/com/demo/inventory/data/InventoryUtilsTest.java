package com.demo.inventory.data;

import com.demo.inventory.configuration.setup.StartDataUserConfig;
import com.demo.inventory.data.mapper.ItemResponseMapper;
import com.demo.inventory.data.utils.InventoryUtils;
import com.demo.inventory.item.model.Folder;
import com.demo.inventory.item.repository.CategoryRepository;
import com.demo.inventory.item.repository.FolderRepository;
import com.demo.inventory.item.repository.ImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class InventoryUtilsTest {

    // Added to avoid conflicts with @PostConstruct
    @MockBean
    private StartDataUserConfig startDataUserConfig;
    @MockBean
    private ImageRepository imageRepository;
    @MockBean
    private CategoryRepository categoryRepository;
    @MockBean
    private FolderRepository folderRepository;
    @MockBean
    private ItemResponseMapper mapper;

    private InventoryUtils inventoryUtils;

    @BeforeEach
    void setUp() {
        inventoryUtils = new InventoryUtils(imageRepository, categoryRepository, folderRepository, mapper);
    }

    @Test
    void shouldGenerateRightPathName() {
        Folder testable = new Folder(4L, "test4", 2L, 1L);
        List<Folder> searchableSpace = List.of(
                new Folder(1L, "test1", null, 1L),
                new Folder(2L, "test2", 1L, 1L),
                new Folder(3L, "test3", 1L, 1L),
                testable,
                new Folder(5L, "test5", 2L, 1L),
                new Folder(6L, "test6", 3L, 1L)
        );

        String expected = "My-Items / test1 / test2 / test4 /";
        String actual = inventoryUtils.getFolderPathName(searchableSpace, testable);
        assertThat(actual).isEqualTo(expected);
    }
}
