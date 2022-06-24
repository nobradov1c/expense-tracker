package com.expensetracker.main.domain.common.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TotalAmountDto {
    private BigDecimal totalAmount;
    private BigDecimal totalExpenses;
    private BigDecimal totalIncomes;
}
