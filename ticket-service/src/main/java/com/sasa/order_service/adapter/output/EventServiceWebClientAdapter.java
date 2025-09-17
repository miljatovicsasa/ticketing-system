package com.sasa.order_service.adapter.output;

import com.sasa.order_service.domain.port.output.EventServicePort;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class EventServiceWebClientAdapter implements EventServicePort {

    private final WebClient webClient;

    public EventServiceWebClientAdapter(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://event-service:8081").build();
    }

    @Override
    public boolean canPurchase(Long eventId, int quantity) {
        Boolean result = webClient.get()
                .uri("/events/{id}/can-purchase?quantity={q}", eventId, quantity)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        return Boolean.TRUE.equals(result);
    }
}
