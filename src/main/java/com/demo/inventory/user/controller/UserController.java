package com.demo.inventory.user.controller;

import com.demo.inventory.security.InventoryUser;
import com.demo.inventory.user.dto.LoginDto;
import com.demo.inventory.user.dto.LoginResponse;
import com.demo.inventory.user.dto.RegisterDto;
import com.demo.inventory.user.service.AuthService;
import com.demo.inventory.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterDto registerDto){
        userService.registerUser(registerDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("login")
    public LoginResponse login(@Valid @RequestBody LoginDto loginDto){
        return authService.login(loginDto);
    }

    @PostMapping("logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal InventoryUser auth) {
        authService.logout(auth.getId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("data")
    public LoginResponse getUserDataByToken(@AuthenticationPrincipal InventoryUser auth) {
        return authService.getUserDataByToken(auth.getId());
    }
}
