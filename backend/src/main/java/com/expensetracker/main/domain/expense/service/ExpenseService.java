package com.expensetracker.main.domain.expense.service;

import java.util.List;

import com.expensetracker.main.domain.expense.dto.ExpenseDto;
import com.expensetracker.main.domain.expense.dto.ExpenseGroupDto;
import com.expensetracker.main.domain.expense.dto.ExpenseGroupResponseDto;
import com.expensetracker.main.domain.expense.dto.ExpenseResponseDto;
import com.expensetracker.main.domain.expense.dto.TotalExpenseAmountDto;
import com.expensetracker.main.domain.expense.entity.ExpenseEntity;
import com.expensetracker.main.domain.expense.entity.ExpenseGroup;

public interface ExpenseService {
    // get all expenses
    List<ExpenseResponseDto> getAllExpenses(Long userId);

    // paging
    List<ExpenseResponseDto> getAllExpenses(Long userId, Integer page, Integer size);

    // all by group
    List<ExpenseResponseDto> getAllExpensesByGroup(Long userId, Long expenseGroupId);

    List<ExpenseResponseDto> getAllExpensesByGroup(Long userId, Long expenseGroupId, Integer page, Integer size);

    // getExpenseById
    ExpenseResponseDto getExpenseById(Long userId, Long expenseId);

    TotalExpenseAmountDto getTotalExpenseAmount(Long userId);

    TotalExpenseAmountDto getTotalExpenseAmount(Long userId, Long expenseGroupId);

    List<ExpenseResponseDto> getLast5ExpenseChanges(Long userId);

    ExpenseEntity createExpense(Long userId, ExpenseDto expenseDto);

    ExpenseEntity updateExpense(Long userId, Long expenseId, ExpenseDto expenseDto);

    void deleteExpense(Long userId, Long expenseId);

    void deleteAllExpenses(Long userId);

    void deleteAllExpensesByGroup(Long userId, Long expenseGroupId);

    List<ExpenseGroupResponseDto> getAllExpenseGroups(Long userId);

    List<ExpenseGroupResponseDto> getAllExpenseGroups(Long userId, Integer page, Integer size);

    ExpenseGroupResponseDto getExpenseGroup(Long userId, Long expenseGroupId);

    ExpenseGroup createExpenseGroup(Long userId, ExpenseGroupDto expenseGroupDto);

    ExpenseGroup updateExpenseGroup(Long userId, Long expenseGroupId, ExpenseGroupDto expenseGroupDto);

    void deleteExpenseGroup(Long userId, Long expenseGroupId);

    void deleteAllExpenseGroups(Long userId);
}
