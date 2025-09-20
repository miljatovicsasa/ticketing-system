package com.sasa.event_service.adapter.input.rest.controller;

import com.sasa.event_service.adapter.input.mapper.EventDtoMapper;
import com.sasa.event_service.adapter.input.dto.request.EventCreateRequestDto;
import com.sasa.event_service.adapter.input.dto.response.EventResponseDto;
import com.sasa.event_service.adapter.input.dto.response.ApiResponseDto;
import com.sasa.event_service.domain.model.Event;
import com.sasa.event_service.port.input.EventUseCasePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Events", description = "Operations for managing events (admin only)")
@RestController
@RequestMapping("/api/admin/events")
public class EventController {

    private final EventUseCasePort eventUseCasePort;

    public EventController(EventUseCasePort eventUseCasePort) {
        this.eventUseCasePort = eventUseCasePort;
    }

    @Operation(summary = "Create a new event", description = "Creates a new event with the specified details. Requires admin authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Event created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid event creation request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - invalid or missing token")
    })
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
