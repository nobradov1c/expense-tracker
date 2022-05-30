package com.expensetracker.main.domain.expense.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TotalExpenseAmountDto {
    private Double totalAmount;
    private Long expenseGroupId;

    public TotalExpenseAmountDto(Double totalAmount) {
        this.totalAmount = totalAmount;
        this.expenseGroupId = null;
    }

    public TotalExpenseAmountDto(Double totalAmount, Long expenseGroupId) {
        this.totalAmount = totalAmount;
        this.expenseGroupId = expenseGroupId;
    }
}
