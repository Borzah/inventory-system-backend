package com.demo.inventory.configuration.setup;

import com.demo.inventory.configuration.UserProperties;
import com.demo.inventory.user.model.User;
import com.demo.inventory.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class StartDataRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserProperties userProperties;
    private final StartDataCategoryConfig categoryConfig;
    private final StartDataFolderConfig folderConfig;
    private final StartDataItemConfig itemConfig;
    private final StartDataImageConfig imageConfig;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername(userProperties.getAdminUsername()).isEmpty()) {

            User admin = new User(
                    userProperties.getAdminUsername(),
                    passwordEncoder.encode(userProperties.getPassword()),
                    userProperties.getAdminRole(),
                    new Timestamp(System.currentTimeMillis())
            );

            User john = new User(
                    userProperties.getUserUsername(),
                    passwordEncoder.encode(userProperties.getPassword()),
                    userProperties.getUserRole(),
                    new Timestamp(System.currentTimeMillis())
            );

            userRepository.saveAll(List.of(admin, john));
            categoryConfig.addStartCategoryDataIfNeeded();
            folderConfig.addStartFolderDataIfNeeded();
            itemConfig.addStartItemDataIfNeeded();
            imageConfig.addStartImageDataIfNeeded();

            log.info("Start data added");
        }
    }
}
