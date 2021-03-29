package com.demo.inventory.user.controller;

import com.demo.inventory.security.Roles;
import com.demo.inventory.user.dto.LoginDto;
import com.demo.inventory.user.dto.LoginResponse;
import com.demo.inventory.user.dto.RegisterDto;
import com.demo.inventory.user.model.User;
import com.demo.inventory.user.service.AuthService;
import com.demo.inventory.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
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

    @PostMapping("logout/{userId}")
    public ResponseEntity<Void> logout(@PathVariable Long userId, @RequestHeader("Authorization") String authToken) {
        authService.logout(userId, authToken);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("data")
    public LoginResponse getUserDataByToken(@RequestHeader("Authorization") String authToken) {
        return authService.getUserDataByToken(authToken);
    }

    @GetMapping(path = "{userId}")
    public User getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

    @GetMapping
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
