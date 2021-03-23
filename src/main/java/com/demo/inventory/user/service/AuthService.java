package com.demo.inventory.user.service;

import com.demo.inventory.security.AuthChecker;
import com.demo.inventory.security.JwtTokenProvider;
import com.demo.inventory.security.MyUser;
import com.demo.inventory.security.UserTokenHolder;
import com.demo.inventory.user.dto.LoginDto;
import com.demo.inventory.user.dto.LoginResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Service
@Transactional
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserTokenHolder userTokenHolder;
    private final AuthChecker authChecker;
    private final UserService userService;

    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserTokenHolder userTokenHolder, AuthChecker authChecker, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userTokenHolder = userTokenHolder;
        this.authChecker = authChecker;
        this.userService = userService;
    }

    public LoginResponse login(LoginDto loginDto) {
//        if (isBlank(loginDto.getUsername())) {
//            throw new UserException("missing username");
//        }
//        if (isBlank(loginDto.getPassword())) {
//            throw new UserException("missing password");
//        }
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        MyUser myUser = (MyUser) authenticate.getPrincipal();
        String token = jwtTokenProvider.generateToken(myUser, myUser.getId());
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
}

