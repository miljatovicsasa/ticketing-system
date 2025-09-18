package com.sasa.ticket_service.domain.service;

import com.sasa.ticket_service.domain.model.Ticket;
import com.sasa.ticket_service.domain.model.TicketStatus;
import com.sasa.ticket_service.port.input.TicketUseCasePort;
import com.sasa.ticket_service.port.output.EventServicePort;
import com.sasa.ticket_service.port.output.TicketRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

        ticket.purchase(eventServicePort);
        try {
            return ticketRepositoryPort.save(ticket);
        } catch (Exception e) {
            ticket.rollbackPurchase(eventServicePort);
            throw e;
        }
    }

    @Override
    public Page<Ticket> getAllTickets(Pageable pageable) {
        return ticketRepositoryPort.getAllTickets(pageable);
    }

    @Override
    public Ticket cancelTicket(UUID ticketId) {
        Ticket ticket = ticketRepositoryPort.findById(ticketId);

        ticket.cancel(eventServicePort);
        try {
            return ticketRepositoryPort.save(ticket);
        } catch (Exception e) {
            ticket.rollbackCancellation(eventServicePort);
            throw e;
        }
    }
}
