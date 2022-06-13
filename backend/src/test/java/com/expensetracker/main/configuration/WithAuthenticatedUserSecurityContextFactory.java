package com.expensetracker.main.configuration;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import com.expensetracker.main.domain.user.dto.UserAuthDto;

public class WithAuthenticatedUserSecurityContextFactory implements WithSecurityContextFactory<WithAuthenticatedUser> {

    @Override
    public SecurityContext createSecurityContext(WithAuthenticatedUser annotation) {
        Long id = annotation.id();
        String email = annotation.email();
        String name = annotation.name();
        String role = annotation.role();

        UserAuthDto principal = UserAuthDto.builder().id(id).email(email).name(name).role(role).build();
        UsernamePasswordAuthenticationToken authUser = new UsernamePasswordAuthenticationToken(principal, "password",
                AuthorityUtils.createAuthorityList(role));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authUser);
        return context;
    }

}
