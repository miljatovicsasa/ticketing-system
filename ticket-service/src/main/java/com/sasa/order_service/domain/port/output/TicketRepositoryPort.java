package com.sasa.order_service.domain.port.output;

import com.sasa.order_service.domain.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TicketRepositoryPort {

    Page<Ticket> getAllTickets(Pageable pageable);
    Optional<Ticket> findById(Long id);
    Ticket save(Ticket ticket);
}
