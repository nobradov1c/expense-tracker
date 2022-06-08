package com.expensetracker.main.domain.income.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IncomeGroupResponseDto {
    private Long id;
    private long createdAt;
    private long updatedAt;

    private String name;
    private String description;
}
