package com.sasa.event_service.adapter.input.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record MarkFinishedEventRequestDto(
        @NotNull(message = "Event ID is required")
        UUID eventId
) { }