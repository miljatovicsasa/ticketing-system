package com.sasa.order_service.adapter.output.repository;

import com.sasa.order_service.adapter.output.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTicketRepository extends JpaRepository<TicketEntity, Long> {

}
