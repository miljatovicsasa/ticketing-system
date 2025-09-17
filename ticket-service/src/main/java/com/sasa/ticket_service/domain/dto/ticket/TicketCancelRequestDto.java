package com.sasa.ticket_service.domain.dto.ticket;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record TicketCancelRequestDto (
        @NotNull(message = "ticketId is required")
        UUID ticketId
){}
