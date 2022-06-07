package com.expensetracker.main.domain.expense.service;

import java.util.List;
import java.util.Optional;

import com.expensetracker.main.domain.expense.dto.ExpenseDto;
import com.expensetracker.main.domain.expense.dto.ExpenseGroupDto;
import com.expensetracker.main.domain.expense.dto.TotalExpenseAmountDto;
import com.expensetracker.main.domain.expense.entity.ExpenseEntity;
import com.expensetracker.main.domain.expense.entity.ExpenseGroup;

public interface ExpenseService {
    // get all expenses
    List<ExpenseEntity> getAllExpenses(Long userId);

    // all by group
    List<ExpenseEntity> getAllExpensesByGroup(Long userId, Long expenseGroupId);

    // getExpenseById
    Optional<ExpenseEntity> getExpenseById(Long userId, Long expenseId);

    TotalExpenseAmountDto getTotalExpenseAmount(Long userId);

    TotalExpenseAmountDto getTotalExpenseAmount(Long userId, Long expenseGroupId);

    List<ExpenseEntity> getLast5ExpenseChanges(Long userId);

    ExpenseEntity createExpense(Long userId, ExpenseDto expenseDto);

    ExpenseEntity updateExpense(Long userId, Long expenseId, ExpenseDto expenseDto);

    void deleteExpense(Long userId, Long expenseId);

    void deleteAllExpenses(Long userId);

    void deleteAllExpensesByGroup(Long userId, Long expenseGroupId);

    List<ExpenseGroup> getAllExpenseGroups(Long userId);

    ExpenseGroup getExpenseGroup(Long userId, Long expenseGroupId);

    ExpenseGroup createExpenseGroup(Long userId, ExpenseGroupDto expenseGroupDto);

    ExpenseGroup updateExpenseGroup(Long userId, Long expenseGroupId, ExpenseGroupDto expenseGroupDto);

    void deleteExpenseGroup(Long userId, Long expenseGroupId);

    void deleteAllExpenseGroups(Long userId);
}
