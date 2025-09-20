package com.sasa.ticket_service.adapter.input.dto.request;

import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request payload for purchasing or cancelling tickets for an event")
public record EventPurchaseAndCancelRequestDto(
        @NotNull
        @Schema(description = "ID of the event", example = "123")
        Long eventId,

        @NotNull
        @Schema(description = "Number of tickets to purchase or cancel", example = "2")
        int quantity
) {}
