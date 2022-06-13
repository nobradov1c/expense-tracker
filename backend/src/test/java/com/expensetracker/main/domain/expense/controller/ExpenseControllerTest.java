package com.expensetracker.main.domain.expense.controller;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;

import com.expensetracker.main.configuration.WithAuthenticatedUser;
import com.expensetracker.main.domain.expense.dto.ExpenseDto;
import com.expensetracker.main.domain.expense.entity.ExpenseEntity;
import com.expensetracker.main.domain.expense.repository.ExpenseRepository;
import com.expensetracker.main.domain.expense.service.ExpenseService;
import com.expensetracker.main.domain.user.entity.RoleEntity;
import com.expensetracker.main.domain.user.entity.UserEntity;
import com.expensetracker.main.domain.user.repository.UserRepository;
import com.expensetracker.main.security.JwtAuthFilter;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ExpenseController.class)
public class ExpenseControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    JwtAuthFilter jwtAuthFilter;

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    ExpenseService expenseService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    ExpenseRepository expenseRepository;

    private UserEntity user;
    private ExpenseDto expenseDto;
    private ExpenseEntity expenseEntity;

    @BeforeEach
    public void setUp() {
        expenseDto = ExpenseDto.builder().amount(new BigDecimal(100)).description("test").build();

        expenseEntity = ExpenseEntity.builder().id(1L).amount(new BigDecimal(100)).description("test")
                .expenseGroup(null).build();

        user = UserEntity.builder().id(1L).name("test").email("user@gmail.com")
                .role(RoleEntity.builder().title("USER").build()).build();

    }

    @Test
    @WithAuthenticatedUser(id = 1L, email = "user@gmail.com", name = "user1", role = "USER")
    public void testCanReadOwnExpense() throws Exception {
        when(expenseRepository.findByIdAndUserId(eq(1L), anyLong())).thenReturn(Optional.of(expenseEntity));

        mockMvc.perform(get("/expenses/1")).andExpect(status().isOk());
    }

    @Test
    @WithAuthenticatedUser
    public void testCreateExpense() throws Exception {
        when(expenseService.createExpense(anyLong(), any(ExpenseDto.class))).thenReturn(expenseEntity);

        // userRepository.findById(userId).get();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        // expenseEntity = expenseRepository.save(expenseEntity);
        when(expenseRepository.save(any(ExpenseEntity.class))).thenReturn(expenseEntity);

        System.out.println("testCreateExpense");
        System.out.println(objectMapper.writeValueAsString(expenseDto));

        mockMvc.perform(post("/expenses").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(expenseDto)))
                .andExpect(status().isCreated());
    }

}
