package com.expensetracker.main.domain.income.controller;

import com.expensetracker.main.domain.income.dto.TotalIncomeAmountDto;
import com.expensetracker.main.domain.income.service.IncomeService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/incomes")
@RequiredArgsConstructor
public class IncomeController {
    private final IncomeService incomeService;

    @GetMapping("/total")
    public TotalIncomeAmountDto getTotalIncomeAmount() {
        return incomeService.getTotalIncomeAmount();
    }

    @GetMapping("/total/group/{incomeGroupId}")
    public TotalIncomeAmountDto getTotalIncomeAmount(Long incomeGroupId) {
        return incomeService.getTotalIncomeAmount(incomeGroupId);
    }
}
