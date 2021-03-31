package com.demo.inventory.item.repository;

import com.demo.inventory.item.model.Category;
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

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ItemRepositoryQueryTest {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setUserId(1L);
        user.setUsername("test");
        user.setPassword("test");
        user.setRole(DbRole.USER);
        user.setDateRegistered(new Timestamp(System.currentTimeMillis()));
        userRepository.save(user);

        Category category = new Category(1L, "testCategory");
        categoryRepository.save(category);
    }

    @AfterEach
    void tearDown() {
        itemRepository.deleteAll();
        categoryRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void shouldValidateCustomQueries() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Item item = Item.builder().itemName("testName").userId(1L).categoryId(1L)
                .description("testDescription").serialNumber("12345").dateAdded(timestamp)
                .itemPrice(12.99f).build();
        itemRepository.save(item);

        List<Item> findByNameResult = itemRepository.searchForItemNameContainingAndUserId("na", 1L);
        List<Item> findByNameResultEmpty = itemRepository.searchForItemNameContainingAndUserId("xz", 1L);
        assertThat(findByNameResult).isNotEmpty();
        assertThat(findByNameResult.get(0).getItemName()).isEqualTo("testName");
        assertThat(findByNameResultEmpty).isEmpty();

        List<Item> findByCategoryResult = itemRepository.searchForItemsByCategory(1L, "cate");
        List<Item> findByCategoryResultEmpty = itemRepository.searchForItemsByCategory(1L, "xz");
        assertThat(findByCategoryResult).isNotEmpty();
        assertThat(findByCategoryResult.get(0).getCategoryId()).isEqualTo(1L);
        assertThat(findByCategoryResultEmpty).isEmpty();

        List<Item> findBySerialNumberResult = itemRepository.searchForSerialNumberContainingAndUserId("123", 1L);
        List<Item> findBySerialNumberResultEmpty = itemRepository.searchForSerialNumberContainingAndUserId("99", 1L);
        assertThat(findBySerialNumberResult).isNotEmpty();
        assertThat(findBySerialNumberResult.get(0).getSerialNumber()).isEqualTo("12345");
        assertThat(findBySerialNumberResultEmpty).isEmpty();

        List<Item> findByDescriptionResult = itemRepository.searchForDescriptionContainingAndUserId("desc", 1L);
        List<Item> findByDescriptionResultEmpty = itemRepository.searchForDescriptionContainingAndUserId("xz", 1L);
        assertThat(findByDescriptionResult).isNotEmpty();
        assertThat(findByDescriptionResult.get(0).getDescription()).isEqualTo("testDescription");
        assertThat(findByDescriptionResultEmpty).isEmpty();

        List<Item> findByPriceResult = itemRepository.searchForItemPriceAndUserId("12", 1L);
        List<Item> findByPriceResultEmpty = itemRepository.searchForItemPriceAndUserId("77", 1L);
        assertThat(findByPriceResult).isNotEmpty();
        assertThat(findByPriceResult.get(0).getItemPrice()).isEqualTo(12.99f);
        assertThat(findByPriceResultEmpty).isEmpty();

        List<Item> findByAllFieldsResult = itemRepository.searchForItemsByALlFields(1L, "te");
        List<Item> findByAllFieldsResultEmpty = itemRepository.searchForItemsByALlFields(1L, "xz");
        assertThat(findByAllFieldsResult).isNotEmpty();
        assertThat(findByAllFieldsResult.get(0).getItemName()).isEqualTo("testName");
        assertThat(findByAllFieldsResultEmpty).isEmpty();

    }
}
