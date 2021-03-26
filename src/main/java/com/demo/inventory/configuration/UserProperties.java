package com.demo.inventory.configuration;

import com.demo.inventory.security.DbRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "user-config")
public class UserProperties {

    private String username;
    private String password;

    public DbRole getRole() {
        return DbRole.ADMIN;
    }
}
