package com.sasa.event_service.adapter.input.rest.controller;

import com.sasa.event_service.adapter.input.mapper.EventDtoMapper;
import com.sasa.event_service.adapter.input.dto.request.EventCreateRequestDto;
import com.sasa.event_service.adapter.input.dto.response.EventResponseDto;
import com.sasa.event_service.adapter.input.dto.response.ApiResponseDto;
import com.sasa.event_service.domain.model.Event;
import com.sasa.event_service.port.input.EventUseCasePort;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin/events")
public class EventController {

    private final EventUseCasePort eventUseCasePort;

    public EventController(EventUseCasePort eventUseCasePort) {
        this.eventUseCasePort = eventUseCasePort;
    }


    @PostMapping
    public ResponseEntity<ApiResponseDto<EventResponseDto>> createEvent(
            @RequestBody @Valid EventCreateRequestDto dto
    ) {
        Event event = Event.createNew(
                dto.name(),
                dto.dateTime(),
                dto.capacity(),
                dto.maxPerPurchase()
        );
        Event createdEvent = eventUseCasePort.createEvent(event);
        return ResponseEntity.status(201).body(
                new ApiResponseDto<>(201, "Event created successfully", EventDtoMapper.domainToResponseDto(createdEvent))
        );
    }
}
