package com.expensetracker.main.domain.income.repository;

import java.util.List;
import java.util.Optional;

import com.expensetracker.main.domain.income.entity.IncomeEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepository extends JpaRepository<IncomeEntity, Long> {
    List<IncomeEntity> findByIncomeGroupId(Long incomeGroupId);

    // find by user_id
    List<IncomeEntity> findByUserId(Long userId);

    // findByIdAndUserId
    Optional<IncomeEntity> findByIdAndUserId(Long incomeId, Long userId);

    // find by user_id and income_group_id
    List<IncomeEntity> findByUserIdAndIncomeGroupId(Long userId, Long incomeGroupId);

    List<IncomeEntity> findFirst5ByOrderByUpdatedAtDesc();

    List<IncomeEntity> findFirst5ByUserIdOrderByUpdatedAtDesc(Long userId);

    // deleteByUserId
    void deleteByUserId(Long userId);

    // deleteByUserIdAndIncomeGroupId
    void deleteByUserIdAndIncomeGroupId(Long userId, Long incomeGroupId);
}
