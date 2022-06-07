package com.expensetracker.main.domain.user.controller;

import javax.validation.Valid;

import com.expensetracker.main.domain.user.dto.UserDto;
import com.expensetracker.main.domain.user.dto.UserLoginDto;
import com.expensetracker.main.domain.user.dto.UserRegistrationDto;
import com.expensetracker.main.domain.user.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public UserDto registration(@RequestBody @Valid UserRegistrationDto registration) {
        return userService.registration(registration);
    }

    @PostMapping("/login")
    public UserDto login(@RequestBody @Valid UserLoginDto login) {
        return userService.login(login);
    }
}
