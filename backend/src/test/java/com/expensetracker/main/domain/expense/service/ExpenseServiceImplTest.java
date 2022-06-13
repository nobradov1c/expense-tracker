package com.expensetracker.main.domain.expense.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.expensetracker.main.domain.expense.dto.ExpenseDto;
import com.expensetracker.main.domain.expense.entity.ExpenseEntity;
import com.expensetracker.main.domain.expense.mapper.ExpenseMapper;
import com.expensetracker.main.domain.expense.repository.ExpenseGroupRepository;
import com.expensetracker.main.domain.expense.repository.ExpenseRepository;
import com.expensetracker.main.domain.user.entity.RoleEntity;
import com.expensetracker.main.domain.user.entity.UserEntity;
import com.expensetracker.main.domain.user.repository.UserRepository;

@ExtendWith(SpringExtension.class)
public class ExpenseServiceImplTest {
    ExpenseServiceImpl expenseService;

    UserEntity userEntity;

    @Mock
    ExpenseRepository expenseRepository;

    @Mock
    ExpenseGroupRepository expenseGroupRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ExpenseMapper expenseMapper;

    private ExpenseDto expenseDto;

    @BeforeEach
    public void setUp() {
        expenseService = new ExpenseServiceImpl(expenseRepository, expenseGroupRepository, userRepository,
                expenseMapper);

        userEntity = UserEntity.builder().id(1L).email("user@gmail.com").name("User").password("password")
                .role(RoleEntity.builder().id(1L).title("USER").build()).build();

        expenseDto = ExpenseDto.builder().description("racun za struju").amount(new BigDecimal(100))
                .expenseGroupId(null).build();

        ExpenseEntity expectedExpense = ExpenseEntity.builder().id(1L).description("racun za struju")
                .amount(new BigDecimal(100)).expenseGroup(null).user(userEntity).build();

        when(userRepository.findById(userEntity.getId())).thenReturn(Optional.of(userEntity));
        when(expenseRepository.save(any(ExpenseEntity.class))).thenReturn(expectedExpense);
    }

    @Test
    public void testCreateExpense() {
        ExpenseEntity actualExpense = expenseService.createExpense(userEntity.getId(), expenseDto);
        Assertions.assertEquals(expenseDto.getDescription(), actualExpense.getDescription());
        Assertions.assertEquals(expenseDto.getAmount(), actualExpense.getAmount());
    }

}
