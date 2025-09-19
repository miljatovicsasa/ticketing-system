package com.sasa.ticket_service.domain.service;

import com.sasa.ticket_service.adapter.input.security.principal.AuthPrincipal;
import com.sasa.ticket_service.domain.model.Ticket;
import com.sasa.ticket_service.port.input.TicketUseCasePort;
import com.sasa.ticket_service.port.output.EventServicePort;
import com.sasa.ticket_service.port.output.TicketRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TicketService implements TicketUseCasePort {

    private final TicketRepositoryPort ticketRepositoryPort;
    private final EventServicePort eventServicePort;

    public TicketService(TicketRepositoryPort ticketRepositoryPort, EventServicePort eventServicePort){
        this.ticketRepositoryPort = ticketRepositoryPort;
        this.eventServicePort = eventServicePort;
    }

    public Ticket purchaseTicket(Ticket ticket, AuthPrincipal principal) {

        ticket.purchase(eventServicePort, principal);
        try {
            return ticketRepositoryPort.save(ticket);
        } catch (Exception e) {
            ticket.rollbackPurchase(eventServicePort, principal);
            throw e;
        }
    }

    @Override
    public Page<Ticket> getAllTickets(Pageable pageable, AuthPrincipal principal) {
        return ticketRepositoryPort.getAllUserTickets(pageable, principal);
    }

    @Override
    public Ticket cancelTicket(Long ticketId, AuthPrincipal principal) {
        Ticket ticket = ticketRepositoryPort.findById(ticketId);

        if (!ticket.getUserId().equals(principal.user().id())) {
            throw new RuntimeException("You can only cancel your own tickets");
        }

        ticket.cancel(eventServicePort, principal);
        try {
            return ticketRepositoryPort.save(ticket);
        } catch (Exception e) {
            ticket.rollbackCancellation(eventServicePort, principal);
            throw e;
        }
    }
}
