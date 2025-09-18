package com.sasa.ticket_service.adapter.input.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sasa.ticket_service.adapter.input.mapper.TicketDtoMapper;
import com.sasa.ticket_service.adapter.input.dto.request.TicketPurchaseRequestDto;
import com.sasa.ticket_service.domain.model.Ticket;
import com.sasa.ticket_service.port.input.TicketUseCasePort;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TicketKafkaListener {

    private final TicketUseCasePort ticketUseCasePort;

    public TicketKafkaListener(TicketUseCasePort ticketUseCasePort) {
        this.ticketUseCasePort = ticketUseCasePort;
    }

    @KafkaListener(topics = "${kafka.ticket.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            TicketPurchaseRequestDto requestDto = objectMapper.readValue(message, TicketPurchaseRequestDto.class);
            Ticket requestTicket = TicketDtoMapper.requestToDomain(requestDto);

            ticketUseCasePort.purchaseTicket(requestTicket );
        } catch (Exception e) {
            throw new RuntimeException("Error while processing Kafka message", e);
        }
    }
}
