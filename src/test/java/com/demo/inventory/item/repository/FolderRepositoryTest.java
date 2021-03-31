package com.demo.inventory.item.repository;

import com.demo.inventory.item.model.Folder;
import com.demo.inventory.security.DbRole;
import com.demo.inventory.user.model.User;
import com.demo.inventory.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FolderRepositoryTest {

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setUserId(1L);
        user.setUsername("test");
        user.setPassword("test");
        user.setRole(DbRole.USER);
        user.setDateRegistered(new Timestamp(System.currentTimeMillis()));
        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        folderRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void shouldSaveFolderAndSaveChildFolderAndFindAll() {
        Folder folder = new Folder("testParent", null, 1L);
        folderRepository.save(folder);

        Optional<Folder> folderOptional = folderRepository.findById(1L);
        assertThat(folderOptional).isPresent()
                .hasValueSatisfying(f -> {
                    assertThat(f.getFolderName()).isEqualTo("testParent");
                });

        Folder folder2 = new Folder("testChild", 1L, 1L);
        folderRepository.save(folder2);
        List<Folder> result = folderRepository.findAllByUserIdAndParentId(1L, 1L);
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getFolderName()).isEqualTo("testChild");

        Optional<Folder> folderOptionalEmpty = folderRepository.findById(0L);
        assertThat(folderOptionalEmpty).isEmpty();

        Folder folder3 = new Folder("test", null, 1L);
        folderRepository.save(folder3);
        List<Folder> folders = folderRepository.findAllByUserId(1L);
        assertThat(folders.size()).isEqualTo(3);
    }
}
