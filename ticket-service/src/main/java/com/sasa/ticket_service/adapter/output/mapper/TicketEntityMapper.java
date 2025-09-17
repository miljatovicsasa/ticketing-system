package com.sasa.ticket_service.adapter.output.mapper;

import com.sasa.ticket_service.domain.model.Ticket;
import com.sasa.ticket_service.adapter.output.entity.TicketEntity;
import com.sasa.ticket_service.domain.model.TicketStatus;

public class TicketEntityMapper {


    public static TicketEntity DomainToEntity(Ticket ticket) {
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket must not be null");
        }
        return new TicketEntity(
                ticket.getId(),
                ticket.getEventId(),
                ticket.getUserId(),
                ticket.getQuantity(),
                ticket.getStatus().name()
        );
    }

    public static Ticket EntityToDomain(TicketEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity must not be null");
        }
        return new Ticket(
                entity.getId(),
                entity.getEventId(),
                entity.getUserId(),
                entity.getQuantity(),
                TicketStatus.valueOf(entity.getStatus()),
                entity.getPurchaseTime(),
                entity.getCancelTime()
        );
    }
}
