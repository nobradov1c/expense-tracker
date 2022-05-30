package com.expensetracker.main.domain.expense.repository;

import com.expensetracker.main.domain.expense.entity.ExpenseGroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseGroupRepository extends JpaRepository<ExpenseGroup, Long> {

}
