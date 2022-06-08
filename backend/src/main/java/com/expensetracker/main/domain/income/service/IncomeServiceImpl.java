package com.expensetracker.main.domain.income.service;

import java.math.BigDecimal;
import java.util.List;

import com.expensetracker.main.domain.income.dto.IncomeDto;
import com.expensetracker.main.domain.income.dto.IncomeGroupDto;
import com.expensetracker.main.domain.income.dto.IncomeGroupResponseDto;
import com.expensetracker.main.domain.income.dto.IncomeResponseDto;
import com.expensetracker.main.domain.income.dto.TotalIncomeAmountDto;
import com.expensetracker.main.domain.income.entity.IncomeEntity;
import com.expensetracker.main.domain.income.entity.IncomeGroup;
import com.expensetracker.main.domain.income.mapper.IncomeMapper;
import com.expensetracker.main.domain.income.repository.IncomeGroupRepository;
import com.expensetracker.main.domain.income.repository.IncomeRepository;
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
public class IncomeServiceImpl implements IncomeService {
    private final IncomeRepository incomeRepository;
    private final IncomeGroupRepository incomeGroupRepository;
    private final UserRepository userRepository;

    private final IncomeMapper incomeMapper;

    @Override
    public List<IncomeResponseDto> getAllIncomes(Long userId) {
        return incomeRepository.findByUserId(userId).stream().map(incomeMapper::toIncomeResponseDto).toList();
    }

    @Override
    public Page<IncomeResponseDto> getAllIncomes(Long userId, Integer page, Integer size) {
        return incomeRepository.findByUserId(userId, PageRequest.of(page, size)).map(incomeMapper::toIncomeResponseDto);
    }

    @Override
    public List<IncomeResponseDto> getAllIncomesByGroup(Long userId, Long incomeGroupId) {
        IncomeGroup incomeGroup = incomeGroupRepository.findById(incomeGroupId)
                .orElseThrow(() -> new AppException(MyErrorMessages.INCOME_GROUP_NOT_FOUND));

        if (!incomeGroup.getUser().getId().equals(userId)) {
            throw new AppException(MyErrorMessages.INCOME_GROUP_NOT_FOUND);
        }

        return incomeRepository.findByIncomeGroupId(incomeGroupId).stream()
                .map(incomeMapper::toIncomeResponseDto).toList();
    }

    @Override
    public Page<IncomeResponseDto> getAllIncomesByGroup(Long userId, Long incomeGroupId, Integer page, Integer size) {
        IncomeGroup incomeGroup = incomeGroupRepository.findById(incomeGroupId)
                .orElseThrow(() -> new AppException(MyErrorMessages.INCOME_GROUP_NOT_FOUND));

        if (!incomeGroup.getUser().getId().equals(userId)) {
            throw new AppException(MyErrorMessages.INCOME_GROUP_NOT_FOUND);
        }

        return incomeRepository.findByIncomeGroupId(incomeGroupId,
                PageRequest.of(page, size)).map(incomeMapper::toIncomeResponseDto);
    }

    @Override
    public TotalIncomeAmountDto getTotalIncomeAmount(Long userId) {
        BigDecimal totalAmount = new BigDecimal(0);

        List<IncomeEntity> incomes = incomeRepository.findByUserId(userId);

        for (IncomeEntity income : incomes) {
            totalAmount = totalAmount.add(income.getAmount());
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

        List<IncomeEntity> incomes = incomeRepository.findByUserIdAndIncomeGroupId(userId, incomeGroupId);

        for (IncomeEntity income : incomes) {
            totalAmount = totalAmount.add(income.getAmount());
        }

        TotalIncomeAmountDto totalIncomeAmountDto = new TotalIncomeAmountDto(totalAmount, incomeGroupId);

        return totalIncomeAmountDto;
    }

    @Override
    public List<IncomeResponseDto> getLast5IncomeChanges(Long userId) {
        return incomeRepository.findFirst5ByUserIdOrderByUpdatedAtDesc(userId).stream()
                .map(incomeMapper::toIncomeResponseDto).toList();
    }

    @Override
    public IncomeResponseDto getIncomeById(Long userId, Long incomeId) {
        return incomeRepository.findByIdAndUserId(incomeId, userId).map(incomeMapper::toIncomeResponseDto)
                .orElseThrow(() -> new AppException(MyErrorMessages.INCOME_NOT_FOUND));
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

            // check if user is owner of the expense group
            if (!incomeGroup.getUser().getId().equals(userId)) {
                throw new AppException(MyErrorMessages.INCOME_GROUP_NOT_FOUND);
            }

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
    public IncomeEntity updateIncome(Long userId, Long incomeId, IncomeDto incomeDto) {
        IncomeEntity incomeEntity = incomeRepository.findById(incomeId)
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
    public void deleteIncome(Long userId, Long incomeId) {
        IncomeEntity incomeEntity = incomeRepository.findById(incomeId)
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
    public List<IncomeGroupResponseDto> getAllIncomeGroups(Long userId) {
        return incomeGroupRepository.findByUserId(userId).stream()
                .map(incomeMapper::toIncomeGroupResponseDto).toList();
    }

    @Override
    public Page<IncomeGroupResponseDto> getAllIncomeGroups(Long userId, Integer page, Integer size) {
        return incomeGroupRepository.findByUserId(userId, PageRequest.of(page, size))
                .map(incomeMapper::toIncomeGroupResponseDto);
    }

    @Override
    public IncomeGroupResponseDto getIncomeGroup(Long userId, Long incomeGroupId) {
        IncomeGroup incomeGroup = incomeGroupRepository.findById(incomeGroupId)
                .orElseThrow(() -> new AppException(MyErrorMessages.INCOME_GROUP_NOT_FOUND));

        // check if user is owner of the expense group
        if (!incomeGroup.getUser().getId().equals(userId)) {
            throw new AppException(MyErrorMessages.EXPENSE_GROUP_NOT_FOUND);
        }

        return incomeMapper.toIncomeGroupResponseDto(incomeGroup);
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
