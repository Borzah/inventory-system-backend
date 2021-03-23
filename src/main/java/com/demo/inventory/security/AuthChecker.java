package com.demo.inventory.security;

import com.demo.inventory.user.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class AuthChecker {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthChecker(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void checkUserAttachingTheirInfo(Long userId, String authToken) {
        String parentUsername = userService.getUsernameById(userId);
        String requestingUsername = jwtTokenProvider.getUsernameFromToken(authToken.substring(7));
//        if (!parentUsername.equals(requestingUsername)) {
//            throw new AuthorizationException("User can attach only their shopping list!");
//        }
    }
}
