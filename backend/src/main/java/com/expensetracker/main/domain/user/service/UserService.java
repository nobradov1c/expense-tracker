package com.expensetracker.main.domain.user.service;

import java.util.Optional;

import com.expensetracker.main.domain.user.dto.UserAuthDto;
import com.expensetracker.main.domain.user.dto.UserDto;
import com.expensetracker.main.domain.user.dto.UserLoginDto;
import com.expensetracker.main.domain.user.dto.UserRegistrationDto;
import com.expensetracker.main.domain.user.entity.UserEntity;

public interface UserService {
    Optional<UserEntity> findByEmail(String email);

    UserDto registration(final UserRegistrationDto registration);

    UserDto login(final UserLoginDto login);

    UserDto getCurrentUser(final UserAuthDto auth);
}
