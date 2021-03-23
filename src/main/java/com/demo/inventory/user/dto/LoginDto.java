package com.demo.inventory.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class LoginDto {

    @NotBlank(message = "Username cannot be blank!")
    private String username;
    @NotBlank(message = "Password must be not blank!")
    private String password;
}
