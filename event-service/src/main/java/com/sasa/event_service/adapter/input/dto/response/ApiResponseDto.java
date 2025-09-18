package com.sasa.event_service.adapter.input.dto.response;

public record ApiResponseDto<T>(
        int status,
        String message,
        T data
) {
    public ApiResponseDto(int status) {
        this(status, null, null);
    }
}
