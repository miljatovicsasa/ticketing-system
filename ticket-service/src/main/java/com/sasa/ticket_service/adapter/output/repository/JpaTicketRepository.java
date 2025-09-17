package com.sasa.ticket_service.adapter.output.repository;

import com.sasa.ticket_service.adapter.output.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTicketRepository extends JpaRepository<TicketEntity, Long> {

}
