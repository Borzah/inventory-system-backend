package com.demo.inventory.item.repository;

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

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ItemRepositoryTest {

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
    }


    @AfterEach
    void tearDown() {
        itemRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void shouldSaveItemsAndFindThem() {
        Timestamp dateAdded = new Timestamp(System.currentTimeMillis());
        Item item = Item.builder()
                .itemName("test")
                .userId(1L)
                .dateAdded(dateAdded).build();

        itemRepository.save(item);
        Optional<Item> itemOptional = itemRepository.findById(1L);
        assertThat(itemOptional).isPresent()
                .hasValueSatisfying(i -> {
                    assertThat(i.getItemName()).isEqualTo("test");
                });

        Optional<Item> itemOptionalEmpty = itemRepository.findById(0L);
        assertThat(itemOptionalEmpty).isEmpty();

        Item item2 = Item.builder()
                .itemName("test2")
                .userId(1L)
                .dateAdded(dateAdded).build();
        Item item3 = Item.builder()
                .itemName("test3")
                .userId(1L)
                .dateAdded(dateAdded).build();

        itemRepository.saveAll(List.of(item2, item3));
        List<Item> result = itemRepository.findAllByUserId(1L);
        assertThat(result.size()).isEqualTo(3);
    }
}
