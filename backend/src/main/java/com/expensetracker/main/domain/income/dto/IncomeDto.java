package com.expensetracker.main.domain.income.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
@JsonTypeName("income")
// @JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use =
// JsonTypeInfo.Id.NAME)
public class IncomeDto {
    @NotBlank(message = "description is required")
    private String description;

    @NotNull(message = "amount is required")
    private BigDecimal amount;

    // optional field for income group
    private Long incomeGroupId;
}
