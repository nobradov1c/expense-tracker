package com.expensetracker.main.domain.common.service;

import java.util.List;

import com.expensetracker.main.domain.common.dto.TotalAmountDto;
import com.expensetracker.main.domain.expense.entity.ExpenseEntity;
import com.expensetracker.main.domain.expense.repository.ExpenseRepository;
import com.expensetracker.main.domain.income.entity.IncomeEntity;
import com.expensetracker.main.domain.income.repository.IncomeRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TotalAmountServiceImpl implements TotalAmountService {
    private final ExpenseRepository expenseRepository;
    private final IncomeRepository incomeRepository;

    @Override
    public TotalAmountDto getTotalAmount() {
        Double totalExpenses = 0.0;

        List<ExpenseEntity> expenses = expenseRepository.findAll();

        for (ExpenseEntity expense : expenses) {
            totalExpenses += expense.getAmount();
        }

        Double totalIncome = 0.0;

        List<IncomeEntity> incomes = incomeRepository.findAll();

        for (IncomeEntity income : incomes) {
            totalIncome += income.getAmount();
        }

        TotalAmountDto totalAmountDto = new TotalAmountDto(totalIncome - totalExpenses);

        return totalAmountDto;
    }

}
