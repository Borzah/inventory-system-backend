package com.demo.inventory.configuration;

import com.demo.inventory.security.DbRole;
import com.demo.inventory.user.model.User;
import com.demo.inventory.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
public class UserConfig {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserConfig(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void addAdminUser() {
        if (userRepository.findAllByUsername("admin@mail.com").size() == 0) {
            User admin = new User("admin@mail.com", passwordEncoder.encode("password"), DbRole.ADMIN, new Date());
            userRepository.save(admin);
        }
    }
}

