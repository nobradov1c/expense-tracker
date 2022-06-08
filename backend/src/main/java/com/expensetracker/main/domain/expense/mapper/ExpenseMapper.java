package com.expensetracker.main.domain.expense.mapper;

import org.springframework.stereotype.Component;

import com.expensetracker.main.domain.expense.dto.ExpenseGroupResponseDto;
import com.expensetracker.main.domain.expense.dto.ExpenseResponseDto;
import com.expensetracker.main.domain.expense.entity.ExpenseEntity;
import com.expensetracker.main.domain.expense.entity.ExpenseGroup;

@Component
public class ExpenseMapper {
    public ExpenseResponseDto toExpenseResponseDto(ExpenseEntity expense) {
        return ExpenseResponseDto.builder()
                .id(expense.getId())
                .createdAt(expense.getCreatedAt())
                .updatedAt(expense.getUpdatedAt())
                .description(expense.getDescription())
                .amount(expense.getAmount())
                .expenseGroupId(expense.getExpenseGroup().getId())
                .build();
    }

    public ExpenseGroupResponseDto toExpenseGroupResponseDto(ExpenseGroup expenseGroup) {
        return ExpenseGroupResponseDto.builder()
                .id(expenseGroup.getId())
                .createdAt(expenseGroup.getCreatedAt())
                .updatedAt(expenseGroup.getUpdatedAt())
                .name(expenseGroup.getName())
                .description(expenseGroup.getDescription())
                .build();
    }
}
