package com.sasa.order_service.domain.service;

import com.sasa.order_service.domain.exception.TicketNotFoundException;
import com.sasa.order_service.domain.model.Ticket;
import com.sasa.order_service.domain.model.TicketStatus;
import com.sasa.order_service.domain.port.input.CancellTicketUseCase;
import com.sasa.order_service.domain.port.input.GetAllTicketsUseCase;
import com.sasa.order_service.domain.port.input.PurchaseTicketUseCase;
import com.sasa.order_service.domain.port.output.EventServicePort;
import com.sasa.order_service.domain.port.output.TicketRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService implements PurchaseTicketUseCase, GetAllTicketsUseCase, CancellTicketUseCase {

    private final TicketRepositoryPort ticketRepositoryPort;
    private final EventServicePort eventServicePort;

    public TicketService(TicketRepositoryPort ticketRepositoryPort, EventServicePort eventServicePort){
        this.ticketRepositoryPort = ticketRepositoryPort;
        this.eventServicePort = eventServicePort;
    }

    @Override
    public Ticket purchaseTicket(Ticket ticket) {
        if (!eventServicePort.canPurchase(ticket.getEventId(), ticket.getQuantity())) {
            throw new RuntimeException("Not enough tickets or exceeds max per purchase");
        }
        ticket.setUserId(456154L);
        return ticketRepositoryPort.save(ticket);
    }

    @Override
    public Page<Ticket> getAllTickets(Pageable pageable) {
        return ticketRepositoryPort.getAllTickets(pageable);
    }

    @Override
    public Ticket cancellTicket(long ticketId) {
        Ticket ticket = ticketRepositoryPort.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException(ticketId));

        ticket.setStatus(TicketStatus.CANCELED);
        ticket.setCancelTime(LocalDateTime.now());

        return ticketRepositoryPort.save(ticket);
    }
}
