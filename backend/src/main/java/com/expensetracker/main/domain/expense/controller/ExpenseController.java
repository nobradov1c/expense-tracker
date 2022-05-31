package com.expensetracker.main.domain.expense.controller;

import java.util.List;

import com.expensetracker.main.domain.expense.dto.TotalExpenseAmountDto;
import com.expensetracker.main.domain.expense.entity.ExpenseEntity;
import com.expensetracker.main.domain.expense.service.ExpenseService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;

    @GetMapping("/total")
    public TotalExpenseAmountDto getTotalExpenseAmount() {
        return expenseService.getTotalExpenseAmount();
    }

    @GetMapping("/total/group/{expenseGroupId}")
    public TotalExpenseAmountDto getTotalExpenseAmount(Long expenseGroupId) {
        return expenseService.getTotalExpenseAmount(expenseGroupId);
    }

    // last 5 expense changes
    @GetMapping("/last-5-changes")
    public List<ExpenseEntity> getLast5ExpenseChanges() {
        return expenseService.getLast5ExpenseChanges();
    }
}
