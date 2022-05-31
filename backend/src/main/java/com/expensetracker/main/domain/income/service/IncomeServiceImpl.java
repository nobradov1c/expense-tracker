package com.expensetracker.main.domain.income.service;

import java.util.List;

import com.expensetracker.main.domain.income.dto.TotalIncomeAmountDto;
import com.expensetracker.main.domain.income.entity.IncomeEntity;
import com.expensetracker.main.domain.income.repository.IncomeRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IncomeServiceImpl implements IncomeService {
    private final IncomeRepository incomeRepository;

    @Override
    public TotalIncomeAmountDto getTotalIncomeAmount() {
        Double totalAmount = 0.0;

        List<IncomeEntity> incomes = incomeRepository.findAll();

        for (IncomeEntity income : incomes) {
            totalAmount += income.getAmount();
        }

        TotalIncomeAmountDto totalIncomeAmountDto = new TotalIncomeAmountDto(totalAmount);
        return totalIncomeAmountDto;
    }

    @Override
    public TotalIncomeAmountDto getTotalIncomeAmount(Long incomeGroupId) {
        Double totalAmount = 0.0;

        List<IncomeEntity> incomes = incomeRepository.findByIncomeGroupId(incomeGroupId);

        for (IncomeEntity income : incomes) {
            totalAmount += income.getAmount();
        }

        TotalIncomeAmountDto totalIncomeAmountDto = new TotalIncomeAmountDto(totalAmount, incomeGroupId);

        return totalIncomeAmountDto;
    }

    @Override
    public List<IncomeEntity> getLast5IncomeChanges() {
        return incomeRepository.findFirst5ByOrderByUpdatedAtDesc();
    }

}
