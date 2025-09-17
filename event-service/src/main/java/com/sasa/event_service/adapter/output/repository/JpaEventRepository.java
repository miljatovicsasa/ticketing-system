package com.sasa.event_service.adapter.output.repository;

import com.sasa.event_service.adapter.output.entity.EventEntity;
import com.sasa.event_service.domain.model.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaEventRepository extends JpaRepository<EventEntity, UUID> {
    List<EventEntity> findByStatusIn(List<EventStatus> statuses);
}
