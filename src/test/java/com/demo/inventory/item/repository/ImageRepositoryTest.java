package com.demo.inventory.item.repository;

import com.demo.inventory.item.model.Image;
import com.demo.inventory.item.model.Item;
import com.demo.inventory.security.DbRole;
import com.demo.inventory.user.model.User;
import com.demo.inventory.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ImageRepositoryTest {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ItemRepository itemRepository;

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

        Item item = Item.builder().itemName("test").userId(1L)
                .dateAdded(new Timestamp(System.currentTimeMillis())).build();
        itemRepository.save(item);
    }

    @AfterEach
    void tearDown() {
        imageRepository.deleteAll();
        userRepository.deleteAll();
        itemRepository.deleteAll();
    }

    @Test
    void shouldAddImage() throws IOException {
        byte[] testImageBytes = Files.readAllBytes(Paths.get("src/test/resources/assets/wire.jpg"));
        Image image = new Image(1L, testImageBytes);

        imageRepository.save(image);
        Optional<Image> imageOptional = imageRepository.findById(1L);

        assertThat(imageOptional).isPresent()
                .hasValueSatisfying(i -> {
                    assertThat(i.getImageBytes()).isEqualTo(testImageBytes);
                });

        Optional<Image> imageOptionalEmpty = imageRepository.findById(2L);
        assertThat(imageOptionalEmpty).isEmpty();
    }
}
