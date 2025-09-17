package com.sasa.ticket_service.domain.dto.ticket;

import java.util.UUID;

public record TicketResponseDto(
        UUID id,
        UUID eventId,
        UUID userId,
        int quantity,
        String status
) { }
