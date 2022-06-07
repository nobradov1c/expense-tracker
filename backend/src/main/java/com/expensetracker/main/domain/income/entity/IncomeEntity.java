package com.expensetracker.main.domain.income.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.expensetracker.main.domain.common.entity.BaseEntity;
import com.expensetracker.main.domain.user.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "incomes")
public class IncomeEntity extends BaseEntity {
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "income_group_id")
    private IncomeGroup incomeGroup;

    // user
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Builder
    public IncomeEntity(Long id, String description, BigDecimal amount, IncomeGroup incomeGroup) {
        super(id);
        this.description = description;
        this.amount = amount;
        this.incomeGroup = incomeGroup;
    }
}
