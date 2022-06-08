package com.expensetracker.main.domain.income.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.expensetracker.main.domain.income.dto.IncomeDto;
import com.expensetracker.main.domain.income.dto.IncomeGroupDto;
import com.expensetracker.main.domain.income.dto.IncomeGroupResponseDto;
import com.expensetracker.main.domain.income.dto.IncomeResponseDto;
import com.expensetracker.main.domain.income.dto.TotalIncomeAmountDto;
import com.expensetracker.main.domain.income.service.IncomeService;
import com.expensetracker.main.domain.user.dto.UserAuthDto;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/incomes")
@RequiredArgsConstructor
public class IncomeController {
    private final IncomeService incomeService;

    // get all incomes
    @GetMapping
    public List<IncomeResponseDto> getAllIncomes(@AuthenticationPrincipal UserAuthDto authUser) {
        return incomeService.getAllIncomes(authUser.getId());
    }

    // get all incomes paging
    @GetMapping("/paging")
    public Page<IncomeResponseDto> getAllIncomes(@AuthenticationPrincipal UserAuthDto authUser,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return incomeService.getAllIncomes(authUser.getId(), page, size);
    }

    // all by group
    @GetMapping("/all/{incomesGroupId}")
    public List<IncomeResponseDto> getAllIncomesByGroup(@AuthenticationPrincipal UserAuthDto authUser,
            @PathVariable Long incomesGroupId) {
        return incomeService.getAllIncomesByGroup(authUser.getId(), incomesGroupId);
    }

    // all by group paging
    @GetMapping("/all/{incomesGroupId}/paging")
    public Page<IncomeResponseDto> getAllIncomesByGroup(@AuthenticationPrincipal UserAuthDto authUser,
            @PathVariable Long incomesGroupId, @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return incomeService.getAllIncomesByGroup(authUser.getId(), incomesGroupId, page, size);
    }

    // get by id
    @GetMapping("/{incomeId}")
    public IncomeResponseDto getIncomesById(@AuthenticationPrincipal UserAuthDto authUser,
            @PathVariable Long incomeId) {
        return incomeService.getIncomeById(authUser.getId(), incomeId);
    }

    @GetMapping("/total")
    public TotalIncomeAmountDto getTotalIncomesAmount(@AuthenticationPrincipal UserAuthDto authUser) {
        return incomeService.getTotalIncomeAmount(authUser.getId());
    }

    @GetMapping("/total/group/{incomesGroupId}")
    public TotalIncomeAmountDto getTotalIncomesAmount(@AuthenticationPrincipal UserAuthDto authUser,
            @PathVariable Long incomesGroupId) {
        return incomeService.getTotalIncomeAmount(authUser.getId(), incomesGroupId);
    }

    // last 5 income changes
    @GetMapping("/last-5-changes")
    public List<IncomeResponseDto> getLast5IncomesChanges(@AuthenticationPrincipal UserAuthDto authUser) {
        return incomeService.getLast5IncomeChanges(authUser.getId());
    }

    // create an income
    @PostMapping
    public ResponseEntity<?> createIncomes(@RequestBody @Valid IncomeDto income,
            @AuthenticationPrincipal UserAuthDto authUser) {
        incomeService.createIncome(authUser.getId(), income);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // update an income
    @PutMapping("/{incomeId}")
    public ResponseEntity<?> updateIncomes(@RequestBody @Valid IncomeDto income,
            @AuthenticationPrincipal UserAuthDto authUser, @PathVariable Long incomeId) {
        incomeService.updateIncome(authUser.getId(), incomeId, income);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    // delete an income
    @DeleteMapping("/{incomeId}")
    public ResponseEntity<?> deleteIncomes(@AuthenticationPrincipal UserAuthDto authUser,
            @PathVariable Long incomeId) {
        incomeService.deleteIncome(authUser.getId(), incomeId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    // delete all incomes
    @DeleteMapping
    public ResponseEntity<?> deleteAllIncomes(@AuthenticationPrincipal UserAuthDto authUser) {
        incomeService.deleteAllIncomes(authUser.getId());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    // delete incomes by group id
    @DeleteMapping("/group/{incomesGroupId}")
    public ResponseEntity<?> deleteIncomesByGroupId(@AuthenticationPrincipal UserAuthDto authUser,
            @PathVariable Long incomesGroupId) {
        incomeService.deleteAllIncomesByGroup(authUser.getId(), incomesGroupId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    // get income groups
    @GetMapping("/groups")
    public List<IncomeGroupResponseDto> getIncomesGroups(@AuthenticationPrincipal UserAuthDto authUser) {
        return incomeService.getAllIncomeGroups(authUser.getId());
    }

    // get income groups paging
    @GetMapping("/groups/paging")
    public Page<IncomeGroupResponseDto> getIncomesGroups(@AuthenticationPrincipal UserAuthDto authUser,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return incomeService.getAllIncomeGroups(authUser.getId(), page, size);
    }

    // get income group by id
    @GetMapping("/groups/{incomesGroupId}")
    public IncomeGroupResponseDto getIncomesGroupById(@AuthenticationPrincipal UserAuthDto authUser,
            @PathVariable Long incomesGroupId) {
        return incomeService.getIncomeGroup(authUser.getId(), incomesGroupId);
    }

    // create an income group
    @PostMapping("/groups")
    @Transactional
    public ResponseEntity<?> createIncomesGroup(@RequestBody @Valid IncomeGroupDto incomesGroup,
            @AuthenticationPrincipal UserAuthDto authUser) {
        incomeService.createIncomeGroup(authUser.getId(), incomesGroup);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // update an income group
    @PutMapping("/groups/{incomesGroupId}")
    @Transactional
    public ResponseEntity<?> updateIncomesGroup(@RequestBody @Valid IncomeGroupDto incomesGroup,
            @AuthenticationPrincipal UserAuthDto authUser, @PathVariable Long incomesGroupId) {
        incomeService.updateIncomeGroup(authUser.getId(), incomesGroupId, incomesGroup);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    // delete an income group
    @DeleteMapping("/groups/{incomesGroupId}")
    @Transactional
    public ResponseEntity<?> deleteIncomesGroup(@AuthenticationPrincipal UserAuthDto authUser,
            @PathVariable Long incomesGroupId) {
        incomeService.deleteIncomeGroup(authUser.getId(), incomesGroupId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    // delete all income groups
    @DeleteMapping("/groups")
    @Transactional
    public ResponseEntity<?> deleteAllIncomesGroups(@AuthenticationPrincipal UserAuthDto authUser) {
        incomeService.deleteAllIncomeGroups(authUser.getId());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
