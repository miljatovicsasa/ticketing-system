package com.sasa.ticket_service.adapter.input.mapper;

import com.sasa.ticket_service.adapter.input.dto.request.TicketPurchaseRequestDto;
import com.sasa.ticket_service.adapter.input.dto.response.TicketResponseDto;
import com.sasa.ticket_service.domain.model.Ticket;
import com.sasa.ticket_service.domain.model.TicketStatus;

import java.time.LocalDateTime;

public class TicketDtoMapper {

    public static Ticket requestToDomain(TicketPurchaseRequestDto dto) {
        return new Ticket(
                null,
                dto.eventId(),
                dto.quantity(),
                TicketStatus.ACTIVE,
                LocalDateTime.now(),
                null
        );
    }

    public static TicketResponseDto DomainToResponseDto(Ticket ticket) {
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket must not be null");
        }
        return new TicketResponseDto(
                ticket.getId(),
                ticket.getEventId(),
                ticket.getUserId(),
                ticket.getQuantity(),
                ticket.getStatus().name()
        );
    }
}
