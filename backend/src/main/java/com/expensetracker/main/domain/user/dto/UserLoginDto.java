package com.expensetracker.main.domain.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
@JsonTypeName("user")
public class UserLoginDto {
    @NotNull
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 32)
    private String password;
}
