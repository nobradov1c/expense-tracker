package com.expensetracker.main.configuration;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithAuthenticatedUserSecurityContextFactory.class)
public @interface WithAuthenticatedUser {
    long id() default 1L;

    String email() default "user@gmail.com";

    String name() default "user";

    String role() default "USER";

}
