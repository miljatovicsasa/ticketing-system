package com.sasa.ticket_service.adapter.input.dto.response;

import java.util.UUID;

public record TicketResponseDto(
        UUID id,
        UUID eventId,
        UUID userId,
        int quantity,
        String status
) { }
