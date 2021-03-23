package com.demo.inventory.security;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class UserTokenHolder {

    private final HashMap<String, String> tokenHolder;

    public UserTokenHolder() {
        this.tokenHolder = new HashMap<>();
    }

    public void addToken(String username, String token) {
        tokenHolder.put(username, token);
    }

    public void removeToken(String username) {
        tokenHolder.remove(username);
    }

    public boolean tokenInWhiteList(String token) {
        return tokenHolder.containsValue(token);
    }
}
