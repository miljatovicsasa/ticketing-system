package com.sasa.ticket_service.adapter.input.dto.request;

import jakarta.validation.constraints.NotNull;

public record TicketPurchaseKafkaDto(
        @NotNull(message = "eventId is required")
        Long eventId,

        @NotNull(message = "quantity is required")
        Integer quantity,

        @NotNull(message = "userToken is required")
        String userToken
) { }
