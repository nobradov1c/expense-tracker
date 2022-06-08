package com.expensetracker.main.domain.expense.controller;

import java.util.List;

import javax.validation.Valid;

import com.expensetracker.main.domain.expense.dto.ExpenseDto;
import com.expensetracker.main.domain.expense.dto.ExpenseGroupDto;
import com.expensetracker.main.domain.expense.dto.ExpenseGroupResponseDto;
import com.expensetracker.main.domain.expense.dto.ExpenseResponseDto;
import com.expensetracker.main.domain.expense.dto.TotalExpenseAmountDto;
import com.expensetracker.main.domain.expense.service.ExpenseService;
import com.expensetracker.main.domain.user.dto.UserAuthDto;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
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
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;

    // get all expenses
    @GetMapping
    public List<ExpenseResponseDto> getAllExpenses(@AuthenticationPrincipal UserAuthDto authUser) {
        return expenseService.getAllExpenses(authUser.getId());
    }

    @GetMapping("/paging")
    public Page<ExpenseResponseDto> getAllExpensesPaging(@AuthenticationPrincipal UserAuthDto authUser,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return expenseService.getAllExpenses(authUser.getId(), page, size);
    }

    // all by group
    @GetMapping("/all/{expenseGroupId}")
    public List<ExpenseResponseDto> getAllExpensesByGroup(@AuthenticationPrincipal UserAuthDto authUser,
            @PathVariable Long expenseGroupId) {
        return expenseService.getAllExpensesByGroup(authUser.getId(), expenseGroupId);
    }

    @GetMapping("/all/{expenseGroupId}/paging")
    public Page<ExpenseResponseDto> getAllExpensesByGroupPaging(@AuthenticationPrincipal UserAuthDto authUser,
            @PathVariable Long expenseGroupId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return expenseService.getAllExpensesByGroup(authUser.getId(), expenseGroupId, page, size);
    }

    // get by id
    @GetMapping("/{expenseId}")
    public ExpenseResponseDto getExpenseById(@AuthenticationPrincipal UserAuthDto authUser,
            @PathVariable Long expenseId) {
        return expenseService.getExpenseById(authUser.getId(), expenseId);
    }

    @GetMapping("/total")
    public TotalExpenseAmountDto getTotalExpenseAmount(@AuthenticationPrincipal UserAuthDto authUser) {
        return expenseService.getTotalExpenseAmount(authUser.getId());
    }

    @GetMapping("/total/group/{expenseGroupId}")
    public TotalExpenseAmountDto getTotalExpenseAmount(@AuthenticationPrincipal UserAuthDto authUser,
            @PathVariable Long expenseGroupId) {
        return expenseService.getTotalExpenseAmount(authUser.getId(), expenseGroupId);
    }

    // last 5 expense changes
    @GetMapping("/last-5-changes")
    public List<ExpenseResponseDto> getLast5ExpenseChanges(@AuthenticationPrincipal UserAuthDto authUser) {
        return expenseService.getLast5ExpenseChanges(authUser.getId());
    }

    // create an expense
    @PostMapping
    public ResponseEntity<?> createExpense(@RequestBody @Valid ExpenseDto expense,
            @AuthenticationPrincipal UserAuthDto authUser) {
        expenseService.createExpense(authUser.getId(), expense);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // update an expense
    @PutMapping("/{expenseId}")
    public ResponseEntity<?> updateExpense(@RequestBody @Valid ExpenseDto expense,
            @AuthenticationPrincipal UserAuthDto authUser, @PathVariable Long expenseId) {
        expenseService.updateExpense(authUser.getId(), expenseId, expense);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    // delete an expense
    @DeleteMapping("/{expenseId}")
    public ResponseEntity<?> deleteExpense(@AuthenticationPrincipal UserAuthDto authUser,
            @PathVariable Long expenseId) {
        expenseService.deleteExpense(authUser.getId(), expenseId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    // delete all expenses
    @DeleteMapping
    public ResponseEntity<?> deleteAllExpenses(@AuthenticationPrincipal UserAuthDto authUser) {
        expenseService.deleteAllExpenses(authUser.getId());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    // delete expenses by group id
    @DeleteMapping("/group/{expenseGroupId}")
    public ResponseEntity<?> deleteExpensesByGroupId(@AuthenticationPrincipal UserAuthDto authUser,
            @PathVariable Long expenseGroupId) {
        expenseService.deleteAllExpensesByGroup(authUser.getId(), expenseGroupId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    // get expense groups
    @GetMapping("/groups")
    public List<ExpenseGroupResponseDto> getExpenseGroups(@AuthenticationPrincipal UserAuthDto authUser) {
        return expenseService.getAllExpenseGroups(authUser.getId());
    }

    // get expense groups paging
    @GetMapping("/groups/paging")
    public Page<ExpenseGroupResponseDto> getExpenseGroupsPaging(@AuthenticationPrincipal UserAuthDto authUser,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return expenseService.getAllExpenseGroups(authUser.getId(), page, size);
    }

    // get expense group by id
    @GetMapping("/groups/{expenseGroupId}")
    public ExpenseGroupResponseDto getExpenseGroupById(@AuthenticationPrincipal UserAuthDto authUser,
            @PathVariable Long expenseGroupId) {
        return expenseService.getExpenseGroup(authUser.getId(), expenseGroupId);
    }

    // create an expense group
    @PostMapping("/groups")
    @Transactional
    public ResponseEntity<?> createExpenseGroup(@RequestBody @Valid ExpenseGroupDto expenseGroup,
            @AuthenticationPrincipal UserAuthDto authUser) {
        expenseService.createExpenseGroup(authUser.getId(), expenseGroup);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // update an expense group
    @PutMapping("/groups/{expenseGroupId}")
    @Transactional
    public ResponseEntity<?> updateExpenseGroup(@RequestBody @Valid ExpenseGroupDto expenseGroup,
            @AuthenticationPrincipal UserAuthDto authUser, @PathVariable Long expenseGroupId) {
        expenseService.updateExpenseGroup(authUser.getId(), expenseGroupId, expenseGroup);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    // delete an expense group
    @DeleteMapping("/groups/{expenseGroupId}")
    @Transactional
    public ResponseEntity<?> deleteExpenseGroup(@AuthenticationPrincipal UserAuthDto authUser,
            @PathVariable Long expenseGroupId) {
        expenseService.deleteExpenseGroup(authUser.getId(), expenseGroupId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    // delete all expense groups
    @DeleteMapping("/groups")
    @Transactional
    public ResponseEntity<?> deleteAllExpenseGroups(@AuthenticationPrincipal UserAuthDto authUser) {
        expenseService.deleteAllExpenseGroups(authUser.getId());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
