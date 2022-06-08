package com.expensetracker.main.domain.expense.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExpenseGroupResponseDto {
    private Long id;
    private long createdAt;
    private long updatedAt;

    private String name;
    private String description;
}
