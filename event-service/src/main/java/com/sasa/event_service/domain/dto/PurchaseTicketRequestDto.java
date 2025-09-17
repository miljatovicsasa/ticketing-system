package com.sasa.event_service.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import java.util.UUID;

public record PurchaseTicketRequestDto(
        @NotNull(message = "Event ID is required")
        UUID eventId,

        @Min(value = 1, message = "Quantity must be at least 1")
        int quantity
) { }
