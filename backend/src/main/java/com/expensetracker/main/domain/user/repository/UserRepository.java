package com.expensetracker.main.domain.user.repository;

import java.util.Optional;

import com.expensetracker.main.domain.user.entity.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
}