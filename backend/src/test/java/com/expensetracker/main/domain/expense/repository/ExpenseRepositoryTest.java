package com.expensetracker.main.domain.expense.repository;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.expensetracker.main.domain.expense.entity.ExpenseEntity;
import com.expensetracker.main.domain.user.entity.UserEntity;
import com.expensetracker.main.domain.user.repository.UserRepository;

@DataJpaTest
@EnableJpaAuditing
public class ExpenseRepositoryTest {
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    private UserEntity user1;
    private UserEntity user2;
    private ExpenseEntity expense1;

    @BeforeEach
    public void setUp() {
        user1 = userRepository.findById(1L).orElseThrow(() -> new RuntimeException("User not found"));
        user2 = userRepository.findById(2L).orElseThrow(() -> new RuntimeException("User not found"));

        expense1 = expenseRepository.save(ExpenseEntity.builder().id(1L).description("racun za struju")
                .amount(new BigDecimal(100)).expenseGroup(null).user(user1).build());
    }

    @Test
    public void whenListExpensesByUserId_thenReturnExpensesHaveUserId() {
        // given
        List<ExpenseEntity> expenses = expenseRepository.findByUserId(user1.getId());

        // then
        Assertions.assertEquals(expense1, expenses.iterator().next());

        // given
        List<ExpenseEntity> expenses2 = expenseRepository.findByUserId(user2.getId());

        // then
        Assertions.assertEquals(0, expenses2.size());
    }

}
