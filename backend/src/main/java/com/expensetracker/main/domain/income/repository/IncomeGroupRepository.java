package com.expensetracker.main.domain.income.repository;

import com.expensetracker.main.domain.income.entity.IncomeGroup;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeGroupRepository extends JpaRepository<IncomeGroup, Long> {
    // find by user_id
    List<IncomeGroup> findByUserId(Long userId);

    // findByIdAndUserId
    Optional<IncomeGroup> findByIdAndUserId(Long incomeGroupId, Long userId);

    // deleteByUserId
    void deleteByUserId(Long userId);
}
