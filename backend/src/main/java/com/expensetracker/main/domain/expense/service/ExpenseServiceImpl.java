package com.expensetracker.main.domain.expense.service;

import java.math.BigDecimal;
import java.util.List;

import com.expensetracker.main.domain.expense.dto.ExpenseDto;
import com.expensetracker.main.domain.expense.dto.ExpenseGroupDto;
import com.expensetracker.main.domain.expense.dto.ExpenseGroupResponseDto;
import com.expensetracker.main.domain.expense.dto.ExpenseResponseDto;
import com.expensetracker.main.domain.expense.dto.TotalExpenseAmountDto;
import com.expensetracker.main.domain.expense.entity.ExpenseEntity;
import com.expensetracker.main.domain.expense.entity.ExpenseGroup;
import com.expensetracker.main.domain.expense.mapper.ExpenseMapper;
import com.expensetracker.main.domain.expense.repository.ExpenseGroupRepository;
import com.expensetracker.main.domain.expense.repository.ExpenseRepository;
import com.expensetracker.main.domain.user.entity.UserEntity;
import com.expensetracker.main.domain.user.repository.UserRepository;
import com.expensetracker.main.exception.AppException;
import com.expensetracker.main.exception.MyErrorMessages;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ExpenseGroupRepository expenseGroupRepository;
    private final UserRepository userRepository;

    private final ExpenseMapper expenseMapper;

    @Override
    public List<ExpenseResponseDto> getAllExpenses(Long userId) {
        return expenseRepository.findByUserId(userId).stream()
                .map(expenseMapper::toExpenseResponseDto).toList();
    }

    @Override
    public Page<ExpenseResponseDto> getAllExpenses(Long userId, Integer page, Integer size) {
        return expenseRepository.findByUserId(userId, PageRequest.of(page, size))
                .map(expenseMapper::toExpenseResponseDto);
    }

    @Override
    public List<ExpenseResponseDto> getAllExpensesByGroup(Long userId, Long expenseGroupId) {
        ExpenseGroup expenseGroup = expenseGroupRepository.findById(expenseGroupId)
                .orElseThrow(() -> new AppException(MyErrorMessages.EXPENSE_GROUP_NOT_FOUND));

        if (!expenseGroup.getUser().getId().equals(userId)) {
            throw new AppException(MyErrorMessages.EXPENSE_GROUP_NOT_FOUND);
        }

        return expenseRepository.findByExpenseGroupId(expenseGroupId).stream()
                .map(expenseMapper::toExpenseResponseDto).toList();
    }

    @Override
    public Page<ExpenseResponseDto> getAllExpensesByGroup(Long userId, Long expenseGroupId, Integer page,
            Integer size) {
        ExpenseGroup expenseGroup = expenseGroupRepository.findById(expenseGroupId)
                .orElseThrow(() -> new AppException(MyErrorMessages.EXPENSE_GROUP_NOT_FOUND));

        if (!expenseGroup.getUser().getId().equals(userId)) {
            throw new AppException(MyErrorMessages.EXPENSE_GROUP_NOT_FOUND);
        }

        return expenseRepository.findByExpenseGroupId(expenseGroupId, PageRequest.of(page, size))
                .map(expenseMapper::toExpenseResponseDto);
    }

    @Override
    public TotalExpenseAmountDto getTotalExpenseAmount(Long userId) {
        BigDecimal totalAmount = new BigDecimal(0);

        List<ExpenseEntity> expenses = expenseRepository.findByUserId(userId);

        for (ExpenseEntity expense : expenses) {
            totalAmount = totalAmount.add(expense.getAmount());
        }

        TotalExpenseAmountDto totalExpenseAmountDto = new TotalExpenseAmountDto(totalAmount);
        return totalExpenseAmountDto;
    }

    @Override
    public TotalExpenseAmountDto getTotalExpenseAmount(Long userId, Long expenseGroupId) {
        BigDecimal totalAmount = new BigDecimal(0);

        ExpenseGroup expenseGroup = expenseGroupRepository.findById(expenseGroupId)
                .orElseThrow(() -> new AppException(MyErrorMessages.EXPENSE_GROUP_NOT_FOUND));

        if (!expenseGroup.getUser().getId().equals(userId)) {
            throw new AppException(MyErrorMessages.EXPENSE_GROUP_NOT_FOUND);
        }

        List<ExpenseEntity> expenses = expenseRepository.findByUserIdAndExpenseGroupId(userId, expenseGroupId);

        for (ExpenseEntity expense : expenses) {
            totalAmount = totalAmount.add(expense.getAmount());
        }

        TotalExpenseAmountDto totalExpenseAmountDto = new TotalExpenseAmountDto(totalAmount, expenseGroupId);

        return totalExpenseAmountDto;
    }

    @Override
    public List<ExpenseResponseDto> getLast5ExpenseChanges(Long userId) {
        return expenseRepository.findFirst5ByUserIdOrderByUpdatedAtDesc(userId).stream()
                .map(expenseMapper::toExpenseResponseDto).toList();
    }

    @Override
    public ExpenseResponseDto getExpenseById(Long userId, Long expenseId) {
        return expenseRepository.findByIdAndUserId(expenseId, userId)
                .map(expenseMapper::toExpenseResponseDto)
                .orElseThrow(() -> new AppException(MyErrorMessages.EXPENSE_NOT_FOUND));
    }

    @Override
    @Transactional
    public ExpenseEntity createExpense(Long userId, ExpenseDto expenseDto) {
        ExpenseEntity expenseEntity = new ExpenseEntity();
        expenseEntity.setAmount(expenseDto.getAmount());
        expenseEntity.setDescription(expenseDto.getDescription());

        if (expenseDto.getExpenseGroupId() != null) {
            ExpenseGroup expenseGroup = expenseGroupRepository.findById(expenseDto.getExpenseGroupId())
                    .orElseThrow(() -> new AppException(MyErrorMessages.EXPENSE_GROUP_NOT_FOUND));

            // check if user is owner of the expense group
            if (!expenseGroup.getUser().getId().equals(userId)) {
                throw new AppException(MyErrorMessages.EXPENSE_GROUP_NOT_FOUND);
            }

            expenseEntity.setExpenseGroup(expenseGroup);
        }

        UserEntity user = userRepository.findById(userId).get();

        expenseEntity.setUser(user);

        expenseRepository.save(expenseEntity);

        user.getExpenses().add(expenseEntity);
        return expenseEntity;
    }

    @Override
    @Transactional
    public ExpenseEntity updateExpense(Long userId, Long expenseId, ExpenseDto expenseDto) {
        ExpenseEntity expenseEntity = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new AppException(MyErrorMessages.EXPENSE_NOT_FOUND));

        ExpenseEntity expense = expenseEntity;

        if (expenseDto.getAmount() != null) {
            expense.setAmount(expenseDto.getAmount());
        }
        if (expenseDto.getDescription() != null) {
            expense.setDescription(expenseDto.getDescription());
        }

        if (expenseDto.getExpenseGroupId() != null) {
            ExpenseGroup expenseGroup = expenseGroupRepository.findById(expenseDto.getExpenseGroupId())
                    .orElseThrow(() -> new AppException(MyErrorMessages.EXPENSE_GROUP_NOT_FOUND));

            expense.setExpenseGroup(expenseGroup);
        }

        expense = expenseRepository.save(expense);
        return expense;
    }

    @Override
    @Transactional
    public void deleteExpense(Long userId, Long expenseId) {
        ExpenseEntity expenseEntity = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new AppException(MyErrorMessages.EXPENSE_NOT_FOUND));

        ExpenseEntity expense = expenseEntity;

        if (expense.getUser().getId().equals(userId)) {
            expenseRepository.delete(expense);
        } else {
            throw (new AppException(MyErrorMessages.EXPENSE_NOT_FOUND));
        }
    }

    @Override
    @Transactional
    public void deleteAllExpenses(Long userId) {
        expenseRepository.deleteByUserId(userId);
    }

    @Override
    @Transactional
    public void deleteAllExpensesByGroup(Long userId, Long expenseGroupId) {
        ExpenseGroup expenseGroup = expenseGroupRepository.findById(expenseGroupId)
                .orElseThrow(() -> new AppException(MyErrorMessages.EXPENSE_GROUP_NOT_FOUND));

        if (!expenseGroup.getUser().getId().equals(userId)) {
            throw (new AppException(MyErrorMessages.EXPENSE_GROUP_NOT_FOUND));
        }

        expenseRepository.deleteByUserIdAndExpenseGroupId(userId, expenseGroupId);
    }

    @Override
    public List<ExpenseGroupResponseDto> getAllExpenseGroups(Long userId) {
        return expenseGroupRepository.findByUserId(userId).stream()
                .map(expenseMapper::toExpenseGroupResponseDto).toList();
    }

    @Override
    public Page<ExpenseGroupResponseDto> getAllExpenseGroups(Long userId, Integer page, Integer size) {
        return expenseGroupRepository.findByUserId(userId, PageRequest.of(page, size))
                .map(expenseMapper::toExpenseGroupResponseDto);
    }

    @Override
    public ExpenseGroupResponseDto getExpenseGroup(Long userId, Long expenseGroupId) {
        ExpenseGroup expenseGroup = expenseGroupRepository.findById(expenseGroupId)
                .orElseThrow(() -> new AppException(MyErrorMessages.EXPENSE_GROUP_NOT_FOUND));

        // check if user is owner of the expense group
        if (!expenseGroup.getUser().getId().equals(userId)) {
            throw new AppException(MyErrorMessages.EXPENSE_GROUP_NOT_FOUND);
        }

        return expenseMapper.toExpenseGroupResponseDto(expenseGroup);
    }

    @Override
    @Transactional
    public ExpenseGroup createExpenseGroup(Long userId, ExpenseGroupDto expenseGroupDto) {
        ExpenseGroup expenseGroup = new ExpenseGroup();
        expenseGroup.setName(expenseGroupDto.getName());
        expenseGroup.setDescription(expenseGroupDto.getDescription());

        UserEntity user = userRepository.findById(userId).get();
        expenseGroup.setUser(user);

        expenseGroup = expenseGroupRepository.save(expenseGroup);

        return expenseGroup;
    }

    @Override
    @Transactional
    public ExpenseGroup updateExpenseGroup(Long userId, Long expenseGroupId, ExpenseGroupDto expenseGroupDto) {
        ExpenseGroup expenseGroup = expenseGroupRepository.findById(expenseGroupId)
                .orElseThrow(() -> new AppException(MyErrorMessages.EXPENSE_GROUP_NOT_FOUND));

        if (!expenseGroup.getUser().getId().equals(userId)) {
            throw (new AppException(MyErrorMessages.EXPENSE_GROUP_NOT_FOUND));
        }

        if (expenseGroupDto.getName() != null) {
            expenseGroup.setName(expenseGroupDto.getName());
        }

        if (expenseGroupDto.getDescription() != null) {
            expenseGroup.setDescription(expenseGroupDto.getDescription());
        }

        expenseGroup = expenseGroupRepository.save(expenseGroup);

        return expenseGroup;
    }

    @Override
    @Transactional
    public void deleteExpenseGroup(Long userId, Long expenseGroupId) {
        ExpenseGroup expenseGroup = expenseGroupRepository.findById(expenseGroupId)
                .orElseThrow(() -> new AppException(MyErrorMessages.EXPENSE_GROUP_NOT_FOUND));

        if (!expenseGroup.getUser().getId().equals(userId)) {
            throw (new AppException(MyErrorMessages.EXPENSE_GROUP_NOT_FOUND));
        }

        expenseGroupRepository.delete(expenseGroup);
    }

    @Override
    @Transactional
    public void deleteAllExpenseGroups(Long userId) {
        expenseGroupRepository.deleteByUserId(userId);
    }

}
