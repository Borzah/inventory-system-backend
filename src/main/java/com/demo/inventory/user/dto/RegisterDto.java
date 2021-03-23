package com.demo.inventory.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class RegisterDto {

    @NotBlank(message = "Username cannot be blank!")
    @Email(message = "Must be valid email address!")
    private String username;
    @NotBlank(message = "Password must be not blank!")
    private String password;
}
