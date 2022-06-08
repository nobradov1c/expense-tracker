package com.expensetracker.main.domain.income.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IncomeResponseDto {
    private Long id;
    private long createdAt;
    private long updatedAt;

    private String description;
    private BigDecimal amount;
    private Long incomeGroupId;
}
