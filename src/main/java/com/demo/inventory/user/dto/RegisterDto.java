package com.demo.inventory.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class RegisterDto {

    @NotBlank(message = "Username cannot be blank!")
    @Email(message = "Must be valid email address!")
    private String username;
    @NotBlank(message = "Password must be not blank!")
    @Size(max = 50, message
            = "Password cannot be over 50 characters")
    private String password;
}
