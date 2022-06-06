package com.expensetracker.main.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtUtilsConfiguration {

    @Bean
    public JwtUtils getJwtUtils(
            @Value("${expensetracker.auth.token.sign-key}") String signKey,
            @Value("${expensetracker.auth.token.valid-time}") Long validTime) throws Exception {
        if (signKey.length() < 32) {
            throw new Exception("signKey must have length at least 32 bytes");
        }
        return new JwtUtils(signKey, validTime);
    }
}
