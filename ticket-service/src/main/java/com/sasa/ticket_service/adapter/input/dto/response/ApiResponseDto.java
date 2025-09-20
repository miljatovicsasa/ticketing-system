package com.sasa.ticket_service.adapter.input.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Generic API response wrapper")
public record ApiResponseDto<T>(
        @Schema(description = "HTTP status code of the response", example = "200")
        int status,

        @Schema(description = "Message describing the result of the API call", example = "Operation successful")
        String message,

        @Schema(description = "Response data payload")
        T data
) {
    public ApiResponseDto(int status) {
        this(status, null, null);
    }
}
