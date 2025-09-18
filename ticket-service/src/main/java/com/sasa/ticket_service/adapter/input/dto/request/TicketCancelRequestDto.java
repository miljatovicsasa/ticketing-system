package com.sasa.ticket_service.adapter.input.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record TicketCancelRequestDto (
        @NotNull(message = "ticketId is required")
        UUID ticketId
){}
