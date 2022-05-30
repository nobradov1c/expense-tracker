package com.expensetracker.main.domain.expense.repository;

import com.expensetracker.main.domain.expense.entity.ExpenseEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {

}
