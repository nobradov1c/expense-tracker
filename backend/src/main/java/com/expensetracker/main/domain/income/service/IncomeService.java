package com.expensetracker.main.domain.income.service;

import java.util.List;

import com.expensetracker.main.domain.income.dto.TotalIncomeAmountDto;
import com.expensetracker.main.domain.income.entity.IncomeEntity;

public interface IncomeService {
    TotalIncomeAmountDto getTotalIncomeAmount();

    TotalIncomeAmountDto getTotalIncomeAmount(Long expenseGroupId);

    // ovo ima vec automatski generisano od stane spring data
    // void addExpense(Long expenseGroupId, String description, Double amount);

    // getLast5IncomeChanges
    List<IncomeEntity> getLast5IncomeChanges();
}
