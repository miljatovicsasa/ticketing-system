package com.sasa.event_service.adapter.output.repository;

import com.sasa.event_service.adapter.output.entity.EventEntity;
import com.sasa.event_service.domain.model.EventStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaEventRepository extends JpaRepository<EventEntity, Long> {
    List<EventEntity> findByStatusIn(List<EventStatus> statuses);
    Page<EventEntity> findByStatus(String status, Pageable pageable);

}
