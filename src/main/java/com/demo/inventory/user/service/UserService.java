package com.demo.inventory.user.service;

import com.demo.inventory.exception.UserException;
import com.demo.inventory.security.DbRole;
import com.demo.inventory.user.dto.RegisterDto;
import com.demo.inventory.user.model.User;
import com.demo.inventory.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(RegisterDto registerDto) {
        if (userRepository.findByUsername(registerDto.getUsername()).isPresent()) {
            throw new UserException("User with this username already exists!");
        }
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole(DbRole.USER);
        user.setDateRegistered(new Timestamp(System.currentTimeMillis()));
        userRepository.save(user);
    }
}

