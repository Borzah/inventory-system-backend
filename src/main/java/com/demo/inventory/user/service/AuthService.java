package com.demo.inventory.user.service;

import com.demo.inventory.exception.UserException;
import com.demo.inventory.security.AuthChecker;
import com.demo.inventory.security.JwtTokenProvider;
import com.demo.inventory.security.MyUser;
import com.demo.inventory.security.UserTokenHolder;
import com.demo.inventory.user.dto.LoginDto;
import com.demo.inventory.user.dto.LoginResponse;
import com.demo.inventory.user.model.User;
import com.demo.inventory.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserTokenHolder userTokenHolder;
    private final AuthChecker authChecker;
    private final UserRepository userRepository;

    public LoginResponse login(LoginDto loginDto) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        MyUser myUser = (MyUser) authenticate.getPrincipal();
        String token = jwtTokenProvider.generateToken(myUser);
        userTokenHolder.addToken(myUser.getId(), token);
        return LoginResponse.builder()
                .userId(myUser.getId())
                .username(myUser.getUsername())
                .token(token)
                .role(myUser.getDbRole())
                .build();
    }

    public void logout(Long userId, String authToken) {
        authChecker.checkUserAttachingTheirInfo(userId, authToken);
        userTokenHolder.removeToken(userId);
    }

    public LoginResponse getUserDataByToken(String authToken) {
        String token = authToken.substring(7);
        Long userId = jwtTokenProvider.getUserIdFromToken(token);
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return LoginResponse.builder()
                    .userId(user.getUserId())
                    .username(user.getUsername())
                    .token(token)
                    .role(user.getRole())
                    .build();
        }
        throw new UserException("User does not exist");
    }
}

