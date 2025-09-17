package com.sasa.event_service.domain.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record MarkFinishedEventRequestDto(
        @NotNull(message = "Event ID is required")
        UUID eventId
) { }