package com.expensetracker.main.domain.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TotalAmountDto {
    private Double totalAmount;

    public TotalAmountDto(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

}
