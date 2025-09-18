package com.sasa.ticket_service.adapter.output;

import com.sasa.ticket_service.adapter.input.dto.request.EventPurchaseAndCancelRequestDto;
import com.sasa.ticket_service.port.output.EventServicePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Component
public class EventServiceWebClientAdapter implements EventServicePort {

    private final WebClient webClient;
    private final String purchaseTicketEndpoint;
    private final String cancelTicketEndpoint;
    private final String rollbackPurchaseTicketEndpoint;
    private final String rollbackCancelTicketEndpoint;

    public EventServiceWebClientAdapter(
            WebClient.Builder builder,
            @Value("${event-service.base-url}") String eventServiceBaseUrl,
            @Value("${event-service.endpoint.purchase-ticket}") String purchaseTicketEndpoint,
            @Value("${event-service.endpoint.rollback-purchase-ticket}") String rollbackPurchaseTicketEndpoint,
            @Value("${event-service.endpoint.cancel-ticket}") String cancelTicketEndpoint,
            @Value("${event-service.endpoint.rollback-cancel-ticket}") String rollbackCancelTicketEndpoint
    ) {
        this.webClient = builder.baseUrl(eventServiceBaseUrl).build();
        this.purchaseTicketEndpoint = purchaseTicketEndpoint;
        this.cancelTicketEndpoint = cancelTicketEndpoint;
        this.rollbackPurchaseTicketEndpoint = rollbackPurchaseTicketEndpoint;
        this.rollbackCancelTicketEndpoint = rollbackCancelTicketEndpoint;
    }

    @Override
    public boolean eventCheckAndReserve(UUID eventId, int quantity) {
        EventPurchaseAndCancelRequestDto request = new EventPurchaseAndCancelRequestDto(eventId, quantity);

        Boolean result = webClient.post()
                .uri(purchaseTicketEndpoint)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        return Boolean.TRUE.equals(result);
    }

    @Override
    public void rollbackReservation(UUID eventId, int quantity) {
        EventPurchaseAndCancelRequestDto request = new EventPurchaseAndCancelRequestDto(eventId, quantity);

        webClient.post()
                .uri(rollbackPurchaseTicketEndpoint)
                .bodyValue(request)
                .retrieve()
                .toBodilessEntity()
                .block();

    }


    @Override
    public boolean eventCancel(UUID eventId, int quantity) {
        EventPurchaseAndCancelRequestDto request = new EventPurchaseAndCancelRequestDto(eventId, quantity);

        Boolean result = webClient.post()
                .uri(cancelTicketEndpoint)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        return Boolean.TRUE.equals(result);
    }

    @Override
    public void rollbackCancellation(UUID eventId, int quantity) {
        EventPurchaseAndCancelRequestDto request = new EventPurchaseAndCancelRequestDto(eventId, quantity);

        webClient.post()
                .uri(rollbackCancelTicketEndpoint)
                .bodyValue(request)
                .retrieve()
                .toBodilessEntity()
                .block();

    }
}
