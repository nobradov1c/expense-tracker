package com.expensetracker.main.domain.user.service;

import java.util.Optional;

import com.expensetracker.main.domain.user.dto.UserAuthDto;
import com.expensetracker.main.domain.user.dto.UserDto;
import com.expensetracker.main.domain.user.dto.UserLoginDto;
import com.expensetracker.main.domain.user.dto.UserRegistraionDto;
import com.expensetracker.main.domain.user.entity.RoleEntity;
import com.expensetracker.main.domain.user.entity.UserEntity;
import com.expensetracker.main.domain.user.repository.RoleRepository;
import com.expensetracker.main.domain.user.repository.UserRepository;
import com.expensetracker.main.exception.AppException;
import com.expensetracker.main.exception.MyErrorMessages;
import com.expensetracker.main.security.JwtUtils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private UserDto convertEntityToDto(UserEntity userEntity) {
        return UserDto.builder().name(userEntity.getName()).email(userEntity.getEmail())
                .role(userEntity.getRole().getTitle()).token(jwtUtils.encode(userEntity.getEmail())).build();
    }

    @Override
    public UserDto registration(final UserRegistraionDto registration) {
        userRepository.findByEmail(registration.getEmail()).ifPresent(user -> {
            throw new AppException(MyErrorMessages.DUPLICATED_USER);
        });
        RoleEntity role = roleRepository.findByTitle("USER").orElseThrow(() -> {
            throw new AppException(MyErrorMessages.ROLE_NOT_FOUND);
        });

        UserEntity userEntity = UserEntity.builder().name(registration.getName()).email(registration.getEmail())
                .role(role).password(passwordEncoder.encode(registration.getPassword())).build();
        userRepository.save(userEntity);

        return convertEntityToDto(userEntity);
    }

    @Override
    public UserDto login(UserLoginDto login) {
        UserEntity userEntity = userRepository.findByEmail(login.getEmail())
                .filter(user -> passwordEncoder.matches(login.getPassword(), user.getPassword()))
                .orElseThrow(() -> new AppException(MyErrorMessages.LOGIN_INFO_INVALID));
        return convertEntityToDto(userEntity);
    }

    @Override
    public UserDto getCurrentUser(UserAuthDto authUser) {
        return new UserDto(authUser.getName(), authUser.getEmail(), authUser.getRole(), null);
    }
}
