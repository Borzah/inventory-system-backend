package com.demo.inventory.item.service;

import com.demo.inventory.configuration.StartDataUserConfig;
import com.demo.inventory.exception.FolderException;
import com.demo.inventory.item.dto.FolderDto;
import com.demo.inventory.item.model.Folder;
import com.demo.inventory.item.repository.FolderRepository;
import com.demo.inventory.item.utils.ItemUtils;
import com.demo.inventory.security.AuthChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class FolderServiceTest {

    @MockBean
    private StartDataUserConfig startDataUserConfig;

    @MockBean
    private FolderRepository folderRepository;

    @MockBean
    private AuthChecker authChecker;

    @MockBean
    private ItemUtils itemUtils;

    private FolderService folderService;
    private FolderDto folderToAdd;
    private Folder folder;

    @BeforeEach
    void setUp() {
        folderService = new FolderService(folderRepository, authChecker, itemUtils);
        folder = Folder.builder().folderName("testFolder").parentId(1L).userId(1L).build();
        folderToAdd = FolderDto.builder().folderName("testFolder").parentId(1L).userId(1L).build();
    }

    @Test
    void shouldAddNotFolder() {
        when(folderRepository.findAllByFolderNameAndUserIdAndParentId("testFolder", 1L, 1L))
                .thenReturn(List.of(folder));

        assertThatThrownBy(() -> folderService.addFolder(folderToAdd, ""))
                .isInstanceOf(FolderException.class)
                .hasMessageContaining("Folder with such name already exists in this section");
    }
}
