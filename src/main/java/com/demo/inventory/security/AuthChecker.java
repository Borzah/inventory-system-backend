package com.demo.inventory.security;

import com.demo.inventory.exception.AuthorizationException;
import com.demo.inventory.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthChecker {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public void checkUserAttachingTheirInfo(Long userId, String authToken) {
        Long tokenUserId = jwtTokenProvider.getUserIdFromToken(authToken.substring(7));
        if (!userId.equals(tokenUserId)) {
            throw new AuthorizationException("User can attach only their folders and items!");
        }
    }
}
