package com.expensetracker.main.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MyErrorMessages {
    DUPLICATED_USER("there is duplicated user information", HttpStatus.UNPROCESSABLE_ENTITY),
    LOGIN_INFO_INVALID("login information is invalid", HttpStatus.UNPROCESSABLE_ENTITY),
    ALREADY_FOLLOWED_USER("already followed user", HttpStatus.UNPROCESSABLE_ENTITY),
    ALREADY_FAVORITED_ARTICLE("already followed user", HttpStatus.UNPROCESSABLE_ENTITY),

    USER_NOT_FOUND("user not found", HttpStatus.NOT_FOUND),
    ROLE_NOT_FOUND("role not found", HttpStatus.NOT_FOUND),
    EXPENSE_NOT_FOUND("expense not found", HttpStatus.NOT_FOUND),
    EXPENSE_GROUP_NOT_FOUND("expense group not found", HttpStatus.NOT_FOUND),
    INCOME_NOT_FOUND("income not found", HttpStatus.NOT_FOUND),
    INCOME_GROUP_NOT_FOUND("income group not found", HttpStatus.NOT_FOUND),
    GENERIC_NOT_FOUND("resource not found", HttpStatus.NOT_FOUND),
    ;

    private final String message;
    private final HttpStatus status;

    MyErrorMessages(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}