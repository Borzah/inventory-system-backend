package com.demo.inventory.configuration;

import com.demo.inventory.user.model.User;
import com.demo.inventory.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.Date;

@Component
@AllArgsConstructor
public class UserConfig {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserProperties userProperties;

    @PostConstruct
    private void addAdminUser() {
        if (userRepository.findAllByUsername(userProperties.getUsername()).size() == 0) {
            User admin = new User(
                    userProperties.getUsername(),
                    passwordEncoder.encode(userProperties.getPassword()),
                    userProperties.getRole(),
                    new Timestamp(System.currentTimeMillis())
                );
            userRepository.save(admin);
        }
    }
}

