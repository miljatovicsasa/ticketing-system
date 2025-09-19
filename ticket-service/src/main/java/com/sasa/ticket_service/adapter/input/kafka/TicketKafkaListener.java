package com.sasa.ticket_service.adapter.input.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sasa.ticket_service.adapter.input.mapper.TicketDtoMapper;
import com.sasa.ticket_service.adapter.input.dto.request.TicketPurchaseRequestDto;
import com.sasa.ticket_service.adapter.input.security.principal.AuthPrincipal;
import com.sasa.ticket_service.adapter.input.security.JwtAuthenticationFilter;
import com.sasa.ticket_service.domain.model.Ticket;
import com.sasa.ticket_service.port.input.TicketUseCasePort;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class TicketKafkaListener {

    private final TicketUseCasePort ticketUseCasePort;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final ObjectMapper objectMapper;

    public TicketKafkaListener(TicketUseCasePort ticketUseCasePort,
                               JwtAuthenticationFilter jwtAuthenticationFilter,
                               ObjectMapper objectMapper) {
        this.ticketUseCasePort = ticketUseCasePort;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "${kafka.ticket.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(String message) {
        try {
            TicketPurchaseRequestDto requestDto = objectMapper.readValue(message, TicketPurchaseRequestDto.class);

            String token = requestDto.userToken();
            AuthPrincipal principal = jwtAuthenticationFilter.fetchPrincipalFromUserService(token);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(principal, null, Collections.emptyList());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(null));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            Ticket ticket = TicketDtoMapper.requestToDomain(requestDto);
            ticketUseCasePort.purchaseTicket(ticket, principal);

        } catch (Exception e) {
            throw new RuntimeException("Error while processing Kafka message", e);
        } finally {
            SecurityContextHolder.clearContext();
        }
    }
}
