package com.expensetracker.main.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserAuthDto {
    private Long id;
    private String email;
    private String name;
    private String role;
}
