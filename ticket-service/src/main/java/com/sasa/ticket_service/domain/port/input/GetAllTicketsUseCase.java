package com.sasa.ticket_service.domain.port.input;

import com.sasa.ticket_service.domain.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetAllTicketsUseCase {
    Page<Ticket> getAllTickets(Pageable pageable);
}
