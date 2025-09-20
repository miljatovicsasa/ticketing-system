package com.sasa.user_service.adapter.input.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UserRegisterResponse", description = "Response DTO returned after successful user registration")
public record UserRegisterResponse(
        @Schema(description = "Unique identifier of the user", example = "1")
        Long id,

        @Schema(description = "Email of the user", example = "john.doe@example.com")
        String email,

        @Schema(description = "Username of the user", example = "john.doe")
        String username,

        @Schema(description = "First name of the user", example = "John")
        String firstName,

        @Schema(description = "Last name of the user", example = "Doe")
        String lastName
) {}
