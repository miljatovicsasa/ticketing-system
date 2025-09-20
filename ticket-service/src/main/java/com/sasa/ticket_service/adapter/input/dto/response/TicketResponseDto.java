package com.sasa.ticket_service.adapter.input.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO representing ticket details")
public record TicketResponseDto(
        @Schema(description = "Unique identifier of the ticket", example = "123")
        Long id,

        @Schema(description = "Identifier of the associated event", example = "456")
        Long eventId,

        @Schema(description = "Identifier of the user who owns the ticket", example = "789")
        Long userId,

        @Schema(description = "Number of tickets purchased", example = "2")
        int quantity,

        @Schema(description = "Current status of the ticket (e.g., PURCHASED, CANCELLED)", example = "PURCHASED")
        String status
) { }
