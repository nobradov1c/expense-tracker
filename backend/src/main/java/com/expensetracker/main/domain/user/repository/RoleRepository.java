package com.expensetracker.main.domain.user.repository;

import java.util.Optional;

import com.expensetracker.main.domain.user.entity.RoleEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByTitle(String title);
}
