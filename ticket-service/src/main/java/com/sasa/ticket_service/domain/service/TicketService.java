package com.sasa.ticket_service.domain.service;

import com.sasa.ticket_service.domain.model.Ticket;
import com.sasa.ticket_service.domain.model.TicketStatus;
import com.sasa.ticket_service.port.input.TicketUseCasePort;
import com.sasa.ticket_service.port.output.EventServicePort;
import com.sasa.ticket_service.port.output.TicketRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TicketService implements TicketUseCasePort {

    private final TicketRepositoryPort ticketRepositoryPort;
    private final EventServicePort eventServicePort;

    public TicketService(TicketRepositoryPort ticketRepositoryPort, EventServicePort eventServicePort){
        this.ticketRepositoryPort = ticketRepositoryPort;
        this.eventServicePort = eventServicePort;
    }

    @Override
    public Ticket purchaseTicket(Ticket ticket) {

        boolean reserved = eventServicePort.eventCheckAndReserve(ticket.getEventId(), ticket.getQuantity());
        if (!reserved) {
            throw new RuntimeException("Not enough tickets or exceeds max per purchase");
        }

        try{
            ticket.setUserId(UUID.randomUUID());
            return ticketRepositoryPort.save(ticket);
        } catch (Exception e) {
            eventServicePort.rollbackReservation(ticket.getEventId(), ticket.getQuantity());
            throw e;
        }
    }

    @Override
    public Page<Ticket> getAllTickets(Pageable pageable) {
        return ticketRepositoryPort.getAllTickets(pageable);
    }

    @Override
    public Ticket cancellTicket(UUID ticketId) {
        Ticket ticket = ticketRepositoryPort.findById(ticketId);

        boolean reserved = eventServicePort.eventCancel(ticket.getEventId(), ticket.getQuantity());
        if (!reserved) {
            throw new RuntimeException("Not enough tickets or exceeds max per purchase");
        }

        try{
            ticket.setStatus(TicketStatus.CANCELED);
            ticket.setCancelTime(LocalDateTime.now());
            return ticketRepositoryPort.save(ticket);
        } catch (Exception e) {
            eventServicePort.rollbackCancellation(ticket.getEventId(), ticket.getQuantity());
            throw e;
        }
    }
}
