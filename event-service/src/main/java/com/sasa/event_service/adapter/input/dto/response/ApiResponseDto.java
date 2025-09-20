package com.sasa.event_service.adapter.input.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ApiResponse", description = "Generic API response wrapper")
public record ApiResponseDto<T>(
        @Schema(description = "HTTP status code of the response", example = "200")
        int status,

        @Schema(description = "Message describing the response", example = "Operation completed successfully")
        String message,

        @Schema(description = "Response payload, can be any type")
        T data
) { }
