package com.expensetracker.main.domain.expense.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonTypeName("expense")
// @JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use =
// JsonTypeInfo.Id.NAME)
public class ExpenseDto {
    @NotBlank(message = "description is required")
    private String description;

    @NotNull(message = "amount is required")
    private BigDecimal amount;

    // optional field for expense group
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long expenseGroupId;
}
