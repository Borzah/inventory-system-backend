package com.demo.inventory.configuration.setup;

import com.demo.inventory.configuration.UserProperties;
import com.demo.inventory.user.model.User;
import com.demo.inventory.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@Component
@AllArgsConstructor
public class StartDataUserConfig {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserProperties userProperties;
    private final StartDataCategoryConfig categoryConfig;
    private final StartDataFolderConfig folderConfig;
    private final StartDataItemConfig itemConfig;
    private final StartDataImageConfig imageConfig;

    @PostConstruct
    private void addStartUserDataIfNeeded() throws IOException {
        if (userRepository.findByUsername(userProperties.getAdminUsername()).isEmpty()) {

            User admin = new User(
                    userProperties.getAdminUsername(),
                    passwordEncoder.encode(userProperties.getPassword()),
                    userProperties.getAdminRole(),
                    new Timestamp(System.currentTimeMillis())
                );

            User john = new User(
                    userProperties.getFirstUsername(),
                    passwordEncoder.encode(userProperties.getPassword()),
                    userProperties.getUserRole(),
                    new Timestamp(System.currentTimeMillis())
            );

            User anna = new User(
                    userProperties.getSecondUsername(),
                    passwordEncoder.encode(userProperties.getPassword()),
                    userProperties.getUserRole(),
                    new Timestamp(System.currentTimeMillis())
            );

            User mike = new User(
                    userProperties.getThirdUsername(),
                    passwordEncoder.encode(userProperties.getPassword()),
                    userProperties.getUserRole(),
                    new Timestamp(System.currentTimeMillis())
            );

            User jane = new User(
                    userProperties.getFourthUsername(),
                    passwordEncoder.encode(userProperties.getPassword()),
                    userProperties.getUserRole(),
                    new Timestamp(System.currentTimeMillis())
            );

            userRepository.saveAll(List.of(admin, john, anna, mike, jane));
            categoryConfig.addStartCategoryDataIfNeeded();
            folderConfig.addStartFolderDataIfNeeded();
            itemConfig.addStartItemDataIfNeeded();
            imageConfig.addStartImageDataIfNeeded();
        }
    }
}
