package com.sasa.event_service.adapter.input.rest.controller;

import com.sasa.event_service.adapter.input.dto.response.ApiResponseDto;
import com.sasa.event_service.adapter.input.dto.response.EventResponseDto;
import com.sasa.event_service.adapter.input.dto.response.PaginatedResponseDto;
import com.sasa.event_service.adapter.input.mapper.EventDtoMapper;
import com.sasa.event_service.domain.model.Event;
import com.sasa.event_service.port.input.EventUseCasePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/events")
public class PublicEventController {

    private final EventUseCasePort eventUseCasePort;

    public PublicEventController(EventUseCasePort eventUseCasePort) {
        this.eventUseCasePort = eventUseCasePort;
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<PaginatedResponseDto<EventResponseDto>>> getPublicEvents(
            @PageableDefault(size = 20, page = 0) Pageable pageable
    ) {
        Page<Event> eventPage = eventUseCasePort.getAllEvents(pageable);
        PaginatedResponseDto<EventResponseDto> paginatedResponse =
                PaginatedResponseDto.fromPage(eventPage, EventDtoMapper::domainToResponseDto);
        return ResponseEntity.ok(
                new ApiResponseDto<>(200, "Public events fetched successfully", paginatedResponse)
        );
    }
}
