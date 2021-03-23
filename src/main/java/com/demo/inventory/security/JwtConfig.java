package com.demo.inventory.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class JwtConfig {

    private final String secret = "70156880-f859-462c-8d3a-48032aacf0c6";
    private final int durationMin = 60;

    public int getDurationMillis() {
        return durationMin * 60 * 1000;
    }
}
