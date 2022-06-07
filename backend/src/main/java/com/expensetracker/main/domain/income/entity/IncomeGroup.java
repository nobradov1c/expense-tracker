package com.expensetracker.main.domain.income.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.expensetracker.main.domain.common.entity.BaseEntity;
import com.expensetracker.main.domain.user.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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

    // user
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Builder
    public IncomeGroup(Long id, String name, String description, UserEntity user) {
        super(id);
        this.name = name;
        this.description = description;
        this.user = user;
    }
}
