package com.sasa.ticket_service.adapter.output;

import com.sasa.ticket_service.adapter.input.security.principal.AuthPrincipal;
import com.sasa.ticket_service.adapter.input.dto.request.EventPurchaseAndCancelRequestDto;
import com.sasa.ticket_service.port.output.EventServicePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.function.Supplier;

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
    public boolean eventCheckAndReserve(Long eventId, int quantity, AuthPrincipal principal) {
        return callWebClient(() -> webClient.post()
                .uri(purchaseTicketEndpoint)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + principal.token())
                .bodyValue(new EventPurchaseAndCancelRequestDto(eventId, quantity))
                .retrieve()
                .bodyToMono(Boolean.class)
                .block());
    }

    @Override
    public boolean eventCancel(Long eventId, int quantity, AuthPrincipal principal) {
        return callWebClient(() -> webClient.post()
                .uri(cancelTicketEndpoint)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + principal.token())
                .bodyValue(new EventPurchaseAndCancelRequestDto(eventId, quantity))
                .retrieve()
                .bodyToMono(Boolean.class)
                .block());
    }

    @Override
    public void rollbackReservation(Long eventId, int quantity, AuthPrincipal principal) {
        callWebClientVoid(() -> webClient.post()
                .uri(rollbackPurchaseTicketEndpoint)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + principal.token())
                .bodyValue(new EventPurchaseAndCancelRequestDto(eventId, quantity))
                .retrieve()
                .toBodilessEntity()
                .block());
    }

    @Override
    public void rollbackCancellation(Long eventId, int quantity, AuthPrincipal principal) {
        callWebClientVoid(() -> webClient.post()
                .uri(rollbackCancelTicketEndpoint)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + principal.token())
                .bodyValue(new EventPurchaseAndCancelRequestDto(eventId, quantity))
                .retrieve()
                .toBodilessEntity()
                .block());
    }

    private boolean callWebClient(Supplier<Boolean> supplier) {
        try {
            Boolean result = supplier.get();
            return Boolean.TRUE.equals(result);
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Event service call failed: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new RuntimeException("Event service call failed due to unexpected error", e);
        }
    }

    private void callWebClientVoid(Runnable runnable) {
        try {
            runnable.run();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Event service call failed: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new RuntimeException("Event service call failed due to unexpected error", e);
        }
    }

}
