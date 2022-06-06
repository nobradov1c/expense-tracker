package com.expensetracker.main.domain.income.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TotalIncomeAmountDto {
    private BigDecimal totalAmount;
    private Long incomeGroupId;

    public TotalIncomeAmountDto(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        this.incomeGroupId = null;
    }

    public TotalIncomeAmountDto(BigDecimal totalAmount, Long incomeGroupId) {
        this.totalAmount = totalAmount;
        this.incomeGroupId = incomeGroupId;
    }
}
