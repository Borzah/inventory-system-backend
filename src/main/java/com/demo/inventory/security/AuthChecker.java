package com.demo.inventory.security;

import com.demo.inventory.exception.AuthorizationException;
import org.springframework.stereotype.Component;

@Component
public class AuthChecker {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthChecker(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void checkUserAttachingTheirInfo(Long userId, String authToken) {
        Long requestingUserId = jwtTokenProvider.getUserIdFromToken(authToken);
        if (!userId.equals(requestingUserId)) {
            throw new AuthorizationException("User can attach only their folders and items!");
        }
    }
}
