package com.sasa.ticket_service.adapter.output;

import com.sasa.ticket_service.adapter.output.entity.TicketEntity;
import com.sasa.ticket_service.adapter.output.mapper.TicketEntityMapper;
import com.sasa.ticket_service.adapter.output.repository.JpaTicketRepository;
import com.sasa.ticket_service.domain.exception.TicketNotFoundException;
import com.sasa.ticket_service.domain.model.Ticket;
import com.sasa.ticket_service.domain.port.output.TicketRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class TicketRepositoryAdapter implements TicketRepositoryPort {

    private final JpaTicketRepository jpaTicketRepository;

    TicketRepositoryAdapter(JpaTicketRepository jpaTicketRepository){
        this.jpaTicketRepository = jpaTicketRepository;
    }

    @Override
    public Ticket save(Ticket ticket) {
        TicketEntity entity = TicketEntityMapper.DomainToEntity(ticket);
        TicketEntity saved = jpaTicketRepository.save(entity);
        return TicketEntityMapper.EntityToDomain(saved);
    };

    @Override
    public Page<Ticket> getAllTickets(Pageable pageable) {
        return jpaTicketRepository.findAll(pageable)
                .map(TicketEntityMapper::EntityToDomain);
    }

    @Override
    public Ticket findById(UUID id) {
        TicketEntity ticketEntity = jpaTicketRepository.findById(id).orElseThrow(() -> new TicketNotFoundException(id));
        return TicketEntityMapper.EntityToDomain(ticketEntity);
    }
}
