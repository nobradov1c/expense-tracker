package com.expensetracker.main.domain.expense.service;

import java.util.List;

import com.expensetracker.main.domain.expense.dto.TotalExpenseAmountDto;
import com.expensetracker.main.domain.expense.entity.ExpenseEntity;

public interface ExpenseService {
    TotalExpenseAmountDto getTotalExpenseAmount();

    TotalExpenseAmountDto getTotalExpenseAmount(Long expenseGroupId);

    // ovo ima vec automatski generisano od stane spring data
    // void addExpense(Long expenseGroupId, String description, Double amount);

    List<ExpenseEntity> getLast5ExpenseChanges();
}
