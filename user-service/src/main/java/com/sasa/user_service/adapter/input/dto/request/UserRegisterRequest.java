package com.sasa.user_service.adapter.input.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRegisterRequest(
        @Email @NotBlank String email,
        @NotBlank String userName,
        @NotBlank String password,
        @NotBlank String firstName,
        @NotBlank String lastName
) {}
