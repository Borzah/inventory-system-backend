package com.demo.inventory.user;

import com.demo.inventory.security.DbRole;
import com.demo.inventory.user.model.User;
import com.demo.inventory.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void shouldAddAndGetUser() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        user.setRole(DbRole.USER);
        user.setDateRegistered(new Timestamp(System.currentTimeMillis()));

        userRepository.save(user);
        List<User> result = userRepository.findAllByUsername("test");

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getUsername()).isEqualTo("test");

        List<User> resultEmpty = userRepository.findAllByUsername("test2");
        assertThat(resultEmpty).isEmpty();
    }
}
