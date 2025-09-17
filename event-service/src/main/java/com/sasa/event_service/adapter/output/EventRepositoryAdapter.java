package com.sasa.event_service.adapter.output;

import com.sasa.event_service.adapter.output.entity.EventEntity;
import com.sasa.event_service.adapter.output.mapper.EventEntityMapper;
import com.sasa.event_service.adapter.output.repository.JpaEventRepository;
import com.sasa.event_service.domain.exception.EventNotFoundException;
import com.sasa.event_service.domain.model.Event;
import com.sasa.event_service.domain.model.EventStatus;
import com.sasa.event_service.domain.port.output.EventRepositoryPort;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class EventRepositoryAdapter implements EventRepositoryPort {

    private final JpaEventRepository jpaEventRepository;

    EventRepositoryAdapter(JpaEventRepository jpaEventRepository) {
        this.jpaEventRepository = jpaEventRepository;
    }

    @Override
    @Transactional
    public Event saveEvent(Event event) {
        EventEntity eventEntity = EventEntityMapper.domainToEntity(event);
        EventEntity savedEvent = jpaEventRepository.save(eventEntity);
        return EventEntityMapper.entityToDomain(savedEvent);
    }

    @Override
    public Page<Event> listEvents(Pageable pageable) {
        return jpaEventRepository.findAll(pageable)
                .map(EventEntityMapper::entityToDomain);
    }

    @Override
    public Event getEvent(UUID eventId) {
        EventEntity event = jpaEventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));
        return Event.fromEntity(event);
    }

    @Override
    public List<Event> findAllOngoingEvents() {
        List<EventStatus> statuses = List.of(EventStatus.ACTIVE, EventStatus.SOLD_OUT);
        List<EventEntity> entities = jpaEventRepository.findByStatusIn(statuses);
        return entities.stream()
                .map(EventEntityMapper::entityToDomain)
                .collect(Collectors.toList());
    }
}
