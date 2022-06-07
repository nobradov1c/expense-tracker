package com.expensetracker.main.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final AuthenticationProvider authenticationProvider;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = jwtUtils.resolveToken(request);
        try {

            if (jwt != null && jwtUtils.validateToken(jwt)) {
                // username is the email
                String username = jwtUtils.getSub(jwt);
                Authentication authentication = authenticationProvider.getAuthentication(username);
                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    SecurityContextHolder.clearContext();
                    // if token is valid but user is not found in database
                    // very unlikely scenario
                    throw new JwtException("Invalid JWT token");
                }
            }
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            // response.getWriter().write("Invalid token");
            // response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            handlerExceptionResolver.resolveException(request, response, null, e);
        }

    }

}
