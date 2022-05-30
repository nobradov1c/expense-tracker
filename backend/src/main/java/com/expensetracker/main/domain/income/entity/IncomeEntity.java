package com.expensetracker.main.domain.income.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.expensetracker.main.domain.common.entity.BaseEntity;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "incomes")
public class IncomeEntity extends BaseEntity {
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "income_group_id")
    private IncomeGroup incomeGroup;

    @Builder
    public IncomeEntity(Long id, String description, Double amount, IncomeGroup incomeGroup) {
        super(id);
        this.description = description;
        this.amount = amount;
        this.incomeGroup = incomeGroup;
    }
}
