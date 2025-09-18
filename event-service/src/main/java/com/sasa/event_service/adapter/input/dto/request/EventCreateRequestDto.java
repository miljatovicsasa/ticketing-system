package com.sasa.event_service.adapter.input.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Future;

import java.time.OffsetDateTime;

public record EventCreateRequestDto(
        @NotNull(message = "Event name is required")
        String name,

        @NotNull(message = "Event dateTime is required")
        @Future(message = "Event dateTime must be in the future")
        OffsetDateTime dateTime,

        @Min(value = 1, message = "Capacity must be at least 1")
        int capacity,

        @Min(value = 1, message = "Max per purchase must be at least 1")
        int maxPerPurchase
) { }
