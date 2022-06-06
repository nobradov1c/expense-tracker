package com.expensetracker.main.domain.expense.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TotalExpenseAmountDto {
    private BigDecimal totalAmount;
    private Long expenseGroupId;

    public TotalExpenseAmountDto(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        this.expenseGroupId = null;
    }

    public TotalExpenseAmountDto(BigDecimal totalAmount, Long expenseGroupId) {
        this.totalAmount = totalAmount;
        this.expenseGroupId = expenseGroupId;
    }
}
