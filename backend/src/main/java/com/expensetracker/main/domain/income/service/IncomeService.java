package com.expensetracker.main.domain.income.service;

import com.expensetracker.main.domain.income.dto.TotalIncomeAmountDto;

public interface IncomeService {
    TotalIncomeAmountDto getTotalIncomeAmount();

    TotalIncomeAmountDto getTotalIncomeAmount(Long expenseGroupId);

    // ovo ima vec automatski generisano od stane spring data
    // void addExpense(Long expenseGroupId, String description, Double amount);
}
