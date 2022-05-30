package com.expensetracker.main.domain.expense.service;

import com.expensetracker.main.domain.expense.dto.TotalExpenseAmountDto;

public interface ExpenseService {
    TotalExpenseAmountDto getTotalExpenseAmount();

    TotalExpenseAmountDto getTotalExpenseAmount(Long expenseGroupId);

    // ovo ima vec automatski generisano od stane spring data
    // void addExpense(Long expenseGroupId, String description, Double amount);
}
