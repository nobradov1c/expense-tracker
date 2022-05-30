package com.expensetracker.main.domain.income.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TotalIncomeAmountDto {
    private Double totalAmount;
    private Long incomeGroupId;

    public TotalIncomeAmountDto(Double totalAmount) {
        this.totalAmount = totalAmount;
        this.incomeGroupId = null;
    }

    public TotalIncomeAmountDto(Double totalAmount, Long incomeGroupId) {
        this.totalAmount = totalAmount;
        this.incomeGroupId = incomeGroupId;
    }
}
