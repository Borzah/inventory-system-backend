package com.demo.inventory.item.repository;

import com.demo.inventory.item.model.Category;
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
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

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
        categoryRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void shouldAddAndFindCategory() {
        Category category = new Category(1L, "test");
        categoryRepository.save(category);
        Optional<Category> categoryOptional = categoryRepository.findById(1L);

        assertThat(categoryOptional).isPresent()
                .hasValueSatisfying(c -> {
                    assertThat(c.getCategoryName()).isEqualTo("test");
                });

        Optional<Category> categoryOptionalEmpty = categoryRepository.findById(0L);
        assertThat(categoryOptionalEmpty).isEmpty();

        Category category2 = new Category(1L, "test2");

        categoryRepository.save(category2);

        List<Category> result = categoryRepository.findAllByUserId(1L);
        assertThat(result.size()).isEqualTo(2);
    }
}
