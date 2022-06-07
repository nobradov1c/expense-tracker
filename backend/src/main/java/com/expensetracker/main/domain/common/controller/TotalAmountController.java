package com.expensetracker.main.domain.common.controller;

import com.expensetracker.main.domain.common.dto.TotalAmountDto;
import com.expensetracker.main.domain.common.service.TotalAmountService;
import com.expensetracker.main.domain.user.dto.UserAuthDto;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class TotalAmountController {
    private final TotalAmountService totalAmountService;

    @GetMapping("/total-amount")
    public TotalAmountDto getTotalAmount(@AuthenticationPrincipal UserAuthDto authUser) {
        return totalAmountService.getTotalAmount(authUser.getId());
    }
}
