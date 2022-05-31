package com.expensetracker.main.domain.income.controller;

import java.util.List;

import com.expensetracker.main.domain.income.dto.TotalIncomeAmountDto;
import com.expensetracker.main.domain.income.entity.IncomeEntity;
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

    // last 5 income changes
    @GetMapping("/last-5-changes")
    public List<IncomeEntity> getLast5IncomeChanges() {
        return incomeService.getLast5IncomeChanges();
    }
}
