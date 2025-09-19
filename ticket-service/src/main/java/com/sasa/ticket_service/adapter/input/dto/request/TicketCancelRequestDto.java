package com.sasa.ticket_service.adapter.input.dto.request;

import jakarta.validation.constraints.NotNull;

public record TicketCancelRequestDto (
        @NotNull(message = "ticketId is required")
        Long ticketId
){}
