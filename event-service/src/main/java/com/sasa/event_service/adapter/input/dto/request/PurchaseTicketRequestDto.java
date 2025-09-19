package com.sasa.event_service.adapter.input.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

public record PurchaseTicketRequestDto(
        @NotNull(message = "Event ID is required")
        Long eventId,

        @Min(value = 1, message = "Quantity must be at least 1")
        int quantity
) { }
