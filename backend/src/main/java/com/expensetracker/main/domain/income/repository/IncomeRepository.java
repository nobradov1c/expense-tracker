package com.expensetracker.main.domain.income.repository;

import java.util.List;

import com.expensetracker.main.domain.income.entity.IncomeEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepository extends JpaRepository<IncomeEntity, Long> {
    List<IncomeEntity> findByIncomeGroupId(Long incomeGroupId);
}
