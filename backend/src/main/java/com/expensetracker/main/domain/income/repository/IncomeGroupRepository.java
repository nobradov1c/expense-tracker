package com.expensetracker.main.domain.income.repository;

import com.expensetracker.main.domain.income.entity.IncomeGroup;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeGroupRepository extends JpaRepository<IncomeGroup, Long> {

}
