package com.expensetracker.main.domain.user.controller;

import com.expensetracker.main.domain.user.dto.UserAuthDto;
import com.expensetracker.main.domain.user.dto.UserDto;
import com.expensetracker.main.domain.user.service.UserService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public UserDto currentUser(@AuthenticationPrincipal UserAuthDto authUser) {
        return userService.getCurrentUser(authUser);
    }
}
