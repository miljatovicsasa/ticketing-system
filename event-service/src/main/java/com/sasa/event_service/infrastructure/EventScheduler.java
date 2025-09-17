package com.sasa.event_service.infrastructure;

import com.sasa.event_service.domain.service.EventService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EventScheduler {

    private final EventService eventService;

    public EventScheduler(EventService eventService) {
        this.eventService = eventService;
    }

    @Scheduled(cron = "0 */5 * * * ?")
    public void checkAndMarkFinishedEvents() {
        eventService.markFinishedEvents();
    }
}
