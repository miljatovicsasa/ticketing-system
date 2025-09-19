package com.sasa.ticket_service.adapter.output.repository;

import com.sasa.ticket_service.adapter.output.entity.TicketEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

public interface JpaTicketRepository extends JpaRepository<TicketEntity, Long> {
    Page<TicketEntity> findByUserIdAndStatus(Long userId, String status, Pageable pageable);

}
