package com.expensetracker.main.domain.income.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.expensetracker.main.domain.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "income_groups")
public class IncomeGroup extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "incomeGroup", cascade = CascadeType.ALL)
    private List<IncomeEntity> incomes = new ArrayList<>();

    @Builder
    public IncomeGroup(Long id, String name, String description) {
        super(id);
        this.name = name;
        this.description = description;
    }
}
