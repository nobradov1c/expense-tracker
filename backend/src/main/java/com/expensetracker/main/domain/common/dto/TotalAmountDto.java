package com.expensetracker.main.domain.common.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TotalAmountDto {
    private BigDecimal totalAmount;

    public TotalAmountDto(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

}
