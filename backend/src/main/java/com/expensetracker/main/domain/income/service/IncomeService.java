package com.expensetracker.main.domain.income.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.expensetracker.main.domain.income.dto.IncomeDto;
import com.expensetracker.main.domain.income.dto.IncomeGroupDto;
import com.expensetracker.main.domain.income.dto.IncomeGroupResponseDto;
import com.expensetracker.main.domain.income.dto.IncomeResponseDto;
import com.expensetracker.main.domain.income.dto.TotalIncomeAmountDto;
import com.expensetracker.main.domain.income.entity.IncomeEntity;
import com.expensetracker.main.domain.income.entity.IncomeGroup;

public interface IncomeService {
    // get all incomes
    List<IncomeResponseDto> getAllIncomes(Long userId);

    Page<IncomeResponseDto> getAllIncomes(Long userId, Integer page, Integer size);

    // all by group
    List<IncomeResponseDto> getAllIncomesByGroup(Long userId, Long incomeGroupId);

    Page<IncomeResponseDto> getAllIncomesByGroup(Long userId, Long incomeGroupId, Integer page, Integer size);

    // getIncomeById
    IncomeResponseDto getIncomeById(Long userId, Long incomeId);

    TotalIncomeAmountDto getTotalIncomeAmount(Long userId);

    TotalIncomeAmountDto getTotalIncomeAmount(Long userId, Long incomeGroupId);

    List<IncomeResponseDto> getLast5IncomeChanges(Long userId);

    IncomeEntity createIncome(Long userId, IncomeDto incomeDto);

    IncomeEntity updateIncome(Long userId, Long incomeId, IncomeDto incomeDto);

    void deleteIncome(Long userId, Long incomeId);

    void deleteAllIncomes(Long userId);

    void deleteAllIncomesByGroup(Long userId, Long incomeGroupId);

    List<IncomeGroupResponseDto> getAllIncomeGroups(Long userId);

    Page<IncomeGroupResponseDto> getAllIncomeGroups(Long userId, Integer page, Integer size);

    IncomeGroupResponseDto getIncomeGroup(Long userId, Long incomeGroupId);

    IncomeGroup createIncomeGroup(Long userId, IncomeGroupDto incomeGroupDto);

    IncomeGroup updateIncomeGroup(Long userId, Long incomeGroupId, IncomeGroupDto incomeGroupDto);

    void deleteIncomeGroup(Long userId, Long incomeGroupId);

    void deleteAllIncomeGroups(Long userId);
}
