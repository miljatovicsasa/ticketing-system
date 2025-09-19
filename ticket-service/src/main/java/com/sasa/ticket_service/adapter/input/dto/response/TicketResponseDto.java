package com.sasa.ticket_service.adapter.input.dto.response;

public record TicketResponseDto(
        Long id,
        Long eventId,
        Long userId,
        int quantity,
        String status
) { }
