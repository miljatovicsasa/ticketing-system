package com.sasa.ticket_service.port.output;

import com.sasa.ticket_service.domain.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface TicketRepositoryPort {

    Page<Ticket> getAllTickets(Pageable pageable);
    Ticket findById(UUID id);
    Ticket save(Ticket ticket);
}
