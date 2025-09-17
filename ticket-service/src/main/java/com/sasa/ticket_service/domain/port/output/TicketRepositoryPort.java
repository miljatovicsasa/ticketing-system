package com.sasa.ticket_service.domain.port.output;

import com.sasa.ticket_service.domain.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TicketRepositoryPort {

    Page<Ticket> getAllTickets(Pageable pageable);
    Optional<Ticket> findById(Long id);
    Ticket save(Ticket ticket);
}
