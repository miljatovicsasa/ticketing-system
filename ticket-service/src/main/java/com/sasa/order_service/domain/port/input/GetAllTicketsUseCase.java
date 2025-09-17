package com.sasa.order_service.domain.port.input;

import com.sasa.order_service.domain.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GetAllTicketsUseCase {
    Page<Ticket> getAllTickets(Pageable pageable);
}
