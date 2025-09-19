package com.sasa.ticket_service.port.output;

import com.sasa.ticket_service.adapter.input.security.principal.AuthPrincipal;
import com.sasa.ticket_service.domain.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface TicketRepositoryPort {

    Page<Ticket> getAllUserTickets(Pageable pageable, AuthPrincipal principal);
    Ticket findById(Long id);
    Ticket save(Ticket ticket);
}
