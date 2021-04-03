package com.demo.inventory.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class InventoryUser extends User {

    private Long id;
    private DbRole dbRole;

    public InventoryUser(String username,
                         String password,
                         Collection<? extends GrantedAuthority> authorities,
                         Long id,
                         DbRole dbRole) {
        super(username, password, authorities);
        this.id = id;
        this.dbRole = dbRole;
    }

    public InventoryUser(String username,
                         String password,
                         boolean enabled,
                         boolean accountNonExpired,
                         boolean credentialsNonExpired,
                         boolean accountNonLocked,
                         Collection<? extends GrantedAuthority> authorities,
                         Long id,
                         DbRole dbRole) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.dbRole = dbRole;
    }
}
