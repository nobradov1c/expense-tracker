package com.expensetracker.main.domain.expense.repository;

import com.expensetracker.main.domain.expense.entity.ExpenseGroup;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseGroupRepository extends JpaRepository<ExpenseGroup, Long> {
    // find by user_id
    List<ExpenseGroup> findByUserId(Long userId);

    // findByIdAndUserId
    Optional<ExpenseGroup> findByIdAndUserId(Long expenseGroupId, Long userId);

    // deleteByUserId
    void deleteByUserId(Long userId);
}
