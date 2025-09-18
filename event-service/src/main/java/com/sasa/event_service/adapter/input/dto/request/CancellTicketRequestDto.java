package com.sasa.event_service.adapter.input.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CancellTicketRequestDto(
        @NotNull(message = "Event ID is required")
        UUID eventId,

        @Min(value = 1, message = "Quantity must be at least 1")
        int quantity
) {}
