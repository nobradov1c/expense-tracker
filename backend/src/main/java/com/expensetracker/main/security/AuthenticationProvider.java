package com.expensetracker.main.security;

import com.expensetracker.main.domain.user.dto.UserAuthDto;
import com.expensetracker.main.domain.user.service.UserDetailsServiceImpl;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthenticationProvider {
    private final UserDetailsServiceImpl userDetailsService;

    public Authentication getAuthentication(String username) {
        MyUserDetails userDetail = (MyUserDetails) userDetailsService.loadUserByUsername(username);

        if (userDetail == null) {
            return null;
        }

        UserAuthDto principal = UserAuthDto.builder()
                .id(userDetail.getId())
                .name(userDetail.getName())
                .email(userDetail.getEmail())
                .role(userDetail.getAuthorities().iterator().next().getAuthority())
                .build();

        return new UsernamePasswordAuthenticationToken(principal, "", userDetail.getAuthorities());
    }
}
