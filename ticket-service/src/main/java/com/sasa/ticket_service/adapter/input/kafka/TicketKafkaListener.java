package com.sasa.ticket_service.adapter.input.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sasa.ticket_service.adapter.input.mapper.TicketDtoMapper;
import com.sasa.ticket_service.domain.dto.ticket.TicketPurchaseRequestDto;
import com.sasa.ticket_service.domain.model.Ticket;
import com.sasa.ticket_service.domain.port.input.PurchaseTicketUseCase;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TicketKafkaListener {

    private final PurchaseTicketUseCase purchaseTicketUseCase;

    public TicketKafkaListener(PurchaseTicketUseCase purchaseTicketUseCase) {
        this.purchaseTicketUseCase = purchaseTicketUseCase;
    }

    @KafkaListener(topics = "${kafka.ticket.topic}", groupId = "${kafka.ticket.group-id}")
    public void listen(String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            TicketPurchaseRequestDto requestDto = objectMapper.readValue(message, TicketPurchaseRequestDto.class);
            Ticket requestTicket = TicketDtoMapper.requestToDomain(requestDto);

            purchaseTicketUseCase.purchaseTicket(requestTicket );
        } catch (Exception e) {
            throw new RuntimeException("Error while processing Kafka message", e);
        }
    }
}
