package com.demo.inventory.user.dto;

import com.demo.inventory.security.DbRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponse {

    private String token;
    private DbRole role;
}
