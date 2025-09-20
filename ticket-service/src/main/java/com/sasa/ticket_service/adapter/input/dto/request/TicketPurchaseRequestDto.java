package com.sasa.ticket_service.adapter.input.dto.request;

import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request payload for purchasing a ticket")
public record TicketPurchaseRequestDto(
        @NotNull(message = "eventId is required")
        @Schema(description = "ID of the event to purchase the ticket for", example = "123")
        Long eventId,

        @NotNull(message = "quantity is required")
        @Schema(description = "Number of tickets to purchase", example = "2")
        Integer quantity
) {}
