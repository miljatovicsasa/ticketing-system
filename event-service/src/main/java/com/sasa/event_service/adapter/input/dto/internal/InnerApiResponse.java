package com.sasa.event_service.adapter.input.dto.internal;

public record InnerApiResponse<T>(int status, String message, T data) {}
