package com.sasa.order_service.adapter.output;

import com.sasa.order_service.adapter.output.entity.TicketEntity;
import com.sasa.order_service.adapter.output.mapper.TicketEntityMapper;
import com.sasa.order_service.adapter.output.repository.JpaTicketRepository;
import com.sasa.order_service.domain.model.Ticket;
import com.sasa.order_service.domain.port.output.TicketRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
    public Optional<Ticket> findById(Long id) {
        return jpaTicketRepository.findById(id)
                .map(TicketEntityMapper::EntityToDomain);
    }

}
