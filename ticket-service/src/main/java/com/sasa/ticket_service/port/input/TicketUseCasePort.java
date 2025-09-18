package com.sasa.ticket_service.port.input;

import com.sasa.ticket_service.domain.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TicketUseCasePort {
    Ticket cancellTicket(UUID ticketId);
    Page<Ticket> getAllTickets(Pageable pageable);
    Ticket purchaseTicket(Ticket ticket);
}
