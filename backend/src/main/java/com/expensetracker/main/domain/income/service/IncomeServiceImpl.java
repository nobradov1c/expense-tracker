package com.expensetracker.main.domain.income.service;

import java.math.BigDecimal;
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
        BigDecimal totalAmount = new BigDecimal(0);

        List<IncomeEntity> incomes = incomeRepository.findAll();

        for (IncomeEntity income : incomes) {
            totalAmount = totalAmount.add(income.getAmount());
        }

        TotalIncomeAmountDto totalIncomeAmountDto = new TotalIncomeAmountDto(totalAmount);
        return totalIncomeAmountDto;
    }

    @Override
    public TotalIncomeAmountDto getTotalIncomeAmount(Long incomeGroupId) {
        BigDecimal totalAmount = new BigDecimal(0);

        List<IncomeEntity> incomes = incomeRepository.findByIncomeGroupId(incomeGroupId);

        for (IncomeEntity income : incomes) {
            totalAmount = totalAmount.add(income.getAmount());
        }

        TotalIncomeAmountDto totalIncomeAmountDto = new TotalIncomeAmountDto(totalAmount, incomeGroupId);

        return totalIncomeAmountDto;
    }

    @Override
    public List<IncomeEntity> getLast5IncomeChanges() {
        return incomeRepository.findFirst5ByOrderByUpdatedAtDesc();
    }

}
