package com.sasa.ticket_service.adapter.input.dto.request;

import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request payload for cancelling a ticket")
public record TicketCancelRequestDto(
        @NotNull(message = "ticketId is required")
        @Schema(description = "ID of the ticket to be cancelled", example = "456")
        Long ticketId
) {}
