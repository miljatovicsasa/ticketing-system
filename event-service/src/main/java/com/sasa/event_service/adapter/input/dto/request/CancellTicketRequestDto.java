package com.sasa.event_service.adapter.input.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CancellTicketRequestDto(
        @NotNull(message = "Event ID is required")
        Long eventId,

        @Min(value = 1, message = "Quantity must be at least 1")
        int quantity
) {}
