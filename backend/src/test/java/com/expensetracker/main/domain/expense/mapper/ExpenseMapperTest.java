package com.expensetracker.main.domain.expense.mapper;

import java.math.BigDecimal;
import java.time.Instant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.expensetracker.main.domain.expense.dto.ExpenseResponseDto;
import com.expensetracker.main.domain.expense.entity.ExpenseEntity;
import com.expensetracker.main.domain.expense.entity.ExpenseGroup;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
public class ExpenseMapperTest {
    @Autowired
    private ExpenseMapper expenseMapper;

    private ExpenseEntity expenseEntity;
    private ExpenseResponseDto expenseResponseDto;

    @BeforeEach
    public void setUp() {
        Long instant = Instant.now().getEpochSecond();
        expenseEntity = ExpenseEntity.builder()
                .id(1L)
                .amount(new BigDecimal(100))
                .description("Test")
                .createdAt(instant)
                .updatedAt(instant)
                .expenseGroup(ExpenseGroup.builder().id(2L).build())
                .build();

        expenseResponseDto = ExpenseResponseDto.builder()
                .id(1L)
                .amount(new BigDecimal(100))
                .description("Test")
                .createdAt(instant)
                .updatedAt(instant)
                .expenseGroupId(2L)
                .build();
    }

    @Test
    public void should_create_ExpenseResponseDto_from_ExpenseEntity() {
        // given
        ExpenseResponseDto expenseResponseDtoTest = expenseMapper.toExpenseResponseDto(expenseEntity);

        // then
        Assertions.assertEquals(expenseResponseDto, expenseResponseDtoTest);
    }

}
