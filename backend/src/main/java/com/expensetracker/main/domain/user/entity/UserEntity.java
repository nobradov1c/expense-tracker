package com.expensetracker.main.domain.user.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.expensetracker.main.domain.common.entity.BaseEntity;
import com.expensetracker.main.domain.expense.entity.ExpenseEntity;
import com.expensetracker.main.domain.income.entity.IncomeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    // role
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;

    // list of expenses
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ExpenseEntity> expenses = new ArrayList<>();

    // list of incomes
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<IncomeEntity> incomes = new ArrayList<>();

    @Builder
    public UserEntity(Long id, String name, String email, String password, RoleEntity role) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
