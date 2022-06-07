package com.expensetracker.main.domain.income.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.expensetracker.main.domain.income.dto.IncomeDto;
import com.expensetracker.main.domain.income.dto.IncomeGroupDto;
import com.expensetracker.main.domain.income.dto.TotalIncomeAmountDto;
import com.expensetracker.main.domain.income.entity.IncomeEntity;
import com.expensetracker.main.domain.income.entity.IncomeGroup;
import com.expensetracker.main.domain.income.repository.IncomeGroupRepository;
import com.expensetracker.main.domain.income.repository.IncomeRepository;
import com.expensetracker.main.domain.user.entity.UserEntity;
import com.expensetracker.main.domain.user.repository.UserRepository;
import com.expensetracker.main.exception.AppException;
import com.expensetracker.main.exception.MyErrorMessages;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IncomeServiceImpl implements IncomeService {
    private final IncomeRepository incomeRepository;
    private final IncomeGroupRepository incomeGroupRepository;
    private final UserRepository userRepository;

    @Override
    public List<IncomeEntity> getAllIncomes(Long userId) {
        return incomeRepository.findByUserId(userId);
    }

    @Override
    public List<IncomeEntity> getAllIncomesByGroup(Long userId, Long incomeGroupId) {
        IncomeGroup incomeGroup = incomeGroupRepository.findById(incomeGroupId)
                .orElseThrow(() -> new AppException(MyErrorMessages.INCOME_GROUP_NOT_FOUND));

        if (!incomeGroup.getUser().getId().equals(userId)) {
            throw new AppException(MyErrorMessages.INCOME_GROUP_NOT_FOUND);
        }

        return incomeRepository.findByIncomeGroupId(incomeGroupId);
    }

    @Override
    public TotalIncomeAmountDto getTotalIncomeAmount(Long userId) {
        BigDecimal totalAmount = new BigDecimal(0);

        List<IncomeEntity> expenses = incomeRepository.findByUserId(userId);

        for (IncomeEntity expense : expenses) {
            totalAmount = totalAmount.add(expense.getAmount());
        }

        TotalIncomeAmountDto totalIncomeAmountDto = new TotalIncomeAmountDto(totalAmount);
        return totalIncomeAmountDto;
    }

    @Override
    public TotalIncomeAmountDto getTotalIncomeAmount(Long userId, Long incomeGroupId) {
        BigDecimal totalAmount = new BigDecimal(0);

        IncomeGroup incomeGroup = incomeGroupRepository.findById(incomeGroupId)
                .orElseThrow(() -> new AppException(MyErrorMessages.INCOME_GROUP_NOT_FOUND));

        if (!incomeGroup.getUser().getId().equals(userId)) {
            throw new AppException(MyErrorMessages.INCOME_GROUP_NOT_FOUND);
        }

        List<IncomeEntity> expenses = incomeRepository.findByUserIdAndIncomeGroupId(userId, incomeGroupId);

        for (IncomeEntity expense : expenses) {
            totalAmount = totalAmount.add(expense.getAmount());
        }

        TotalIncomeAmountDto totalIncomeAmountDto = new TotalIncomeAmountDto(totalAmount, incomeGroupId);

        return totalIncomeAmountDto;
    }

    @Override
    public List<IncomeEntity> getLast5IncomeChanges(Long userId) {
        return incomeRepository.findFirst5ByUserIdOrderByUpdatedAtDesc(userId);
    }

    @Override
    public Optional<IncomeEntity> getIncomeById(Long userId, Long expenseId) {
        return incomeRepository.findByIdAndUserId(expenseId, userId);
    }

    @Override
    @Transactional
    public IncomeEntity createIncome(Long userId, IncomeDto incomeDto) {
        IncomeEntity incomeEntity = new IncomeEntity();
        incomeEntity.setAmount(incomeDto.getAmount());
        incomeEntity.setDescription(incomeDto.getDescription());

        if (incomeDto.getIncomeGroupId() != null) {
            IncomeGroup incomeGroup = incomeGroupRepository.findById(incomeDto.getIncomeGroupId())
                    .orElseThrow(() -> new AppException(MyErrorMessages.INCOME_GROUP_NOT_FOUND));

            incomeEntity.setIncomeGroup(incomeGroup);
        }

        UserEntity user = userRepository.findById(userId).get();

        incomeEntity.setUser(user);

        incomeRepository.save(incomeEntity);

        user.getIncomes().add(incomeEntity);
        return incomeEntity;
    }

    @Override
    @Transactional
    public IncomeEntity updateIncome(Long userId, Long expenseId, IncomeDto incomeDto) {
        IncomeEntity incomeEntity = incomeRepository.findById(expenseId)
                .orElseThrow(() -> new AppException(MyErrorMessages.INCOME_NOT_FOUND));

        if (incomeDto.getAmount() != null) {
            incomeEntity.setAmount(incomeDto.getAmount());
        }
        if (incomeDto.getDescription() != null) {
            incomeEntity.setDescription(incomeDto.getDescription());
        }

        if (incomeDto.getIncomeGroupId() != null) {
            IncomeGroup incomeGroup = incomeGroupRepository.findById(incomeDto.getIncomeGroupId())
                    .orElseThrow(() -> new AppException(MyErrorMessages.INCOME_GROUP_NOT_FOUND));

            incomeEntity.setIncomeGroup(incomeGroup);
        }

        incomeEntity = incomeRepository.save(incomeEntity);
        return incomeEntity;
    }

    @Override
    @Transactional
    public void deleteIncome(Long userId, Long expenseId) {
        IncomeEntity incomeEntity = incomeRepository.findById(expenseId)
                .orElseThrow(() -> new AppException(MyErrorMessages.INCOME_NOT_FOUND));

        if (incomeEntity.getUser().getId().equals(userId)) {
            incomeRepository.delete(incomeEntity);
        } else {
            throw (new AppException(MyErrorMessages.INCOME_NOT_FOUND));
        }
    }

    @Override
    @Transactional
    public void deleteAllIncomes(Long userId) {
        incomeRepository.deleteByUserId(userId);
    }

    @Override
    @Transactional
    public void deleteAllIncomesByGroup(Long userId, Long incomeGroupId) {
        IncomeGroup incomeGroup = incomeGroupRepository.findById(incomeGroupId)
                .orElseThrow(() -> new AppException(MyErrorMessages.INCOME_GROUP_NOT_FOUND));

        if (!incomeGroup.getUser().getId().equals(userId)) {
            throw (new AppException(MyErrorMessages.INCOME_GROUP_NOT_FOUND));
        }

        // batch delete
        incomeRepository.deleteByUserIdAndIncomeGroupId(userId, incomeGroupId);
    }

    @Override
    public List<IncomeGroup> getAllIncomeGroups(Long userId) {
        return incomeGroupRepository.findByUserId(userId);
    }

    @Override
    public IncomeGroup getIncomeGroup(Long userId, Long incomeGroupId) {
        return incomeGroupRepository.findByIdAndUserId(incomeGroupId, userId)
                .orElseThrow(() -> new AppException(MyErrorMessages.INCOME_GROUP_NOT_FOUND));
    }

    @Override
    @Transactional
    public IncomeGroup createIncomeGroup(Long userId, IncomeGroupDto incomeGroupDto) {
        IncomeGroup incomeGroup = new IncomeGroup();
        incomeGroup.setName(incomeGroupDto.getName());
        incomeGroup.setDescription(incomeGroupDto.getDescription());

        UserEntity user = userRepository.findById(userId).get();
        incomeGroup.setUser(user);

        incomeGroup = incomeGroupRepository.save(incomeGroup);

        return incomeGroup;
    }

    @Override
    @Transactional
    public IncomeGroup updateIncomeGroup(Long userId, Long incomeGroupId, IncomeGroupDto incomeGroupDto) {
        IncomeGroup incomeGroup = incomeGroupRepository.findById(incomeGroupId)
                .orElseThrow(() -> new AppException(MyErrorMessages.INCOME_GROUP_NOT_FOUND));

        if (!incomeGroup.getUser().getId().equals(userId)) {
            throw (new AppException(MyErrorMessages.INCOME_GROUP_NOT_FOUND));
        }

        if (incomeGroupDto.getName() != null) {
            incomeGroup.setName(incomeGroupDto.getName());
        }

        if (incomeGroupDto.getDescription() != null) {
            incomeGroup.setDescription(incomeGroupDto.getDescription());
        }

        incomeGroup = incomeGroupRepository.save(incomeGroup);

        return incomeGroup;
    }

    @Override
    @Transactional
    public void deleteIncomeGroup(Long userId, Long incomeGroupId) {
        IncomeGroup incomeGroup = incomeGroupRepository.findById(incomeGroupId)
                .orElseThrow(() -> new AppException(MyErrorMessages.INCOME_GROUP_NOT_FOUND));

        if (!incomeGroup.getUser().getId().equals(userId)) {
            throw (new AppException(MyErrorMessages.INCOME_GROUP_NOT_FOUND));
        }

        incomeGroupRepository.delete(incomeGroup);
    }

    @Override
    @Transactional
    public void deleteAllIncomeGroups(Long userId) {
        incomeGroupRepository.deleteByUserId(userId);
    }

}
