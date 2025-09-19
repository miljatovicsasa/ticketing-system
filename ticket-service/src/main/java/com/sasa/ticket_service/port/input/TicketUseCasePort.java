package com.sasa.ticket_service.port.input;

import com.sasa.ticket_service.adapter.input.security.principal.AuthPrincipal;
import com.sasa.ticket_service.domain.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TicketUseCasePort {
    Ticket cancelTicket(Long ticketId, AuthPrincipal principal);
    Page<Ticket> getAllTickets(Pageable pageable, AuthPrincipal principal);
    Ticket purchaseTicket(Ticket ticket, AuthPrincipal principal);
}
