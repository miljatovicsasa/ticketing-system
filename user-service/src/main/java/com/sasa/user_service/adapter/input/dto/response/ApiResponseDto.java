package com.sasa.user_service.adapter.input.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ApiResponseDto", description = "Generic API response wrapper containing status, message, and data")
public record ApiResponseDto<T>(
        @Schema(description = "HTTP status code of the response", example = "200")
        int status,

        @Schema(description = "Response message", example = "Operation completed successfully")
        String message,

        @Schema(description = "Response payload, generic type")
        T data
) {}
