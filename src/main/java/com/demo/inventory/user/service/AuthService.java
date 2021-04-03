package com.demo.inventory.user.service;

import com.demo.inventory.exception.UserException;
import com.demo.inventory.security.JwtTokenProvider;
import com.demo.inventory.security.InventoryUser;
import com.demo.inventory.security.UserTokenHolder;
import com.demo.inventory.user.dto.LoginDto;
import com.demo.inventory.user.dto.LoginResponse;
import com.demo.inventory.user.model.User;
import com.demo.inventory.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserTokenHolder userTokenHolder;
    private final UserRepository userRepository;

    public LoginResponse login(LoginDto loginDto) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
            );

        InventoryUser inventoryUser = (InventoryUser) authenticate.getPrincipal();
        String token = jwtTokenProvider.generateToken(inventoryUser);
        userTokenHolder.addToken(inventoryUser.getId(), token);

        return LoginResponse.builder()
                .token(token)
                .role(inventoryUser.getDbRole())
                .build();
    }

    public void logout(Long userId) {
        userTokenHolder.removeToken(userId);
    }

    public LoginResponse getUserDataByToken(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserException("User does not exist");
        }

        User user = userOptional.get();
        log.info("Somebody accessed user info by token");

        return LoginResponse.builder()
                .role(user.getRole())
                .build();
    }
}

