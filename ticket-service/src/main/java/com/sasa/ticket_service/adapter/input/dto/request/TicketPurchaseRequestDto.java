package com.sasa.ticket_service.adapter.input.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record TicketPurchaseRequestDto(
        @NotNull(message = "eventId is required")
        UUID eventId,

        @NotNull(message = "quantity is required")
        Integer quantity
) { }
