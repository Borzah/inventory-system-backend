package com.demo.inventory.security;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class UserTokenHolder {

    // holds token of logged in user
    // when logging out, old token gets invalid
    private final HashMap<Long, String> tokenHolder;

    public UserTokenHolder() {
        this.tokenHolder = new HashMap<>();
    }

    public void addToken(Long userId, String token) {
        tokenHolder.put(userId, token);
    }

    public void removeToken(Long userId) {
        tokenHolder.remove(userId);
    }

    public boolean tokenInWhiteList(String token) {
        return tokenHolder.containsValue(token);
    }
}
