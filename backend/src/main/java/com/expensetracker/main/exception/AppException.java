package com.expensetracker.main.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    private final MyErrorMessages error;

    public AppException(MyErrorMessages error) {
        super(error.getMessage());
        this.error = error;
    }
}