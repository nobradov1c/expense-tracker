package com.expensetracker.main.domain.expense.repository;

import java.util.List;
import java.util.Optional;

import com.expensetracker.main.domain.expense.entity.ExpenseEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {
    List<ExpenseEntity> findByExpenseGroupId(Long expenseGroupId);

    // find by user_id
    List<ExpenseEntity> findByUserId(Long userId);

    // findByIdAndUserId
    Optional<ExpenseEntity> findByIdAndUserId(Long expenseId, Long userId);

    // find by user_id and expense_group_id
    List<ExpenseEntity> findByUserIdAndExpenseGroupId(Long userId, Long expenseGroupId);

    List<ExpenseEntity> findFirst5ByOrderByUpdatedAtDesc();

    List<ExpenseEntity> findFirst5ByUserIdOrderByUpdatedAtDesc(Long userId);

    // deleteByUserId
    void deleteByUserId(Long userId);

    // deleteByUserIdAndExpenseGroupId
    void deleteByUserIdAndExpenseGroupId(Long userId, Long expenseGroupId);
}
