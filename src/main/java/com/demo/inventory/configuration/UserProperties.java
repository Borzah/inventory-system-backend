package com.demo.inventory.configuration;

import com.demo.inventory.security.DbRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "user-config")
@Profile("dev")
public class UserProperties {

    private String adminUsername;

    private String firstUsername;

    private String secondUsername;

    private String thirdUsername;

    private String fourthUsername;

    private String password;

    public DbRole getAdminRole() {
        return DbRole.ADMIN;
    }

    public DbRole getUserRole() {
        return DbRole.USER;
    }
}
