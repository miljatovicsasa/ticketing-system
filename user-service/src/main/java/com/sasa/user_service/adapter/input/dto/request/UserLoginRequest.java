package com.sasa.user_service.adapter.input.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "UserLoginRequest", description = "Request DTO for user login, contains username and password")
public record UserLoginRequest(
        @NotBlank
        @Schema(description = "The username of the user", example = "john.doe")
        String username,

        @NotBlank
        @Schema(description = "The password of the user", example = "P@ssw0rd")
        String password
) {}
