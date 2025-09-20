package com.sasa.user_service.adapter.input.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "UserRegisterRequest", description = "Request DTO for registering a new user")
public record UserRegisterRequest(
        @Email
        @NotBlank
        @Schema(description = "The email of the user", example = "john.doe@example.com")
        String email,

        @NotBlank
        @Schema(description = "The username of the user", example = "john.doe")
        String userName,

        @NotBlank
        @Schema(description = "The password of the user", example = "P@ssw0rd123")
        String password,

        @NotBlank
        @Schema(description = "The first name of the user", example = "John")
        String firstName,

        @NotBlank
        @Schema(description = "The last name of the user", example = "Doe")
        String lastName
) {}
