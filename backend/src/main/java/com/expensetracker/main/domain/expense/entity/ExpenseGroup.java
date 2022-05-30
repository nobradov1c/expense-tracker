package com.expensetracker.main.domain.expense.entity;

import com.expensetracker.main.domain.common.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "expense_groups")
public class ExpenseGroup extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "expenseGroup", cascade = CascadeType.ALL)
    private List<ExpenseEntity> expenses = new ArrayList<>();

    @Builder
    public ExpenseGroup(Long id, String name, String description) {
        super(id);
        this.name = name;
        this.description = description;
    }
}
