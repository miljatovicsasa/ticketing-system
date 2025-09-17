package com.sasa.event_service.adapter.input.rest;

import com.sasa.event_service.adapter.input.mapper.EventDtoMapper;
import com.sasa.event_service.domain.dto.CancellTicketRequestDto;
import com.sasa.event_service.domain.dto.EventCreateRequestDto;
import com.sasa.event_service.domain.dto.EventResponseDto;
import com.sasa.event_service.domain.dto.PurchaseTicketRequestDto;
import com.sasa.event_service.domain.dto.common.ApiResponseDto;
import com.sasa.event_service.domain.dto.common.PaginatedResponseDto;
import com.sasa.event_service.domain.model.Event;
import com.sasa.event_service.domain.port.input.CancelTicketUseCase;
import com.sasa.event_service.domain.port.input.CreateEventUseCase;
import com.sasa.event_service.domain.port.input.GetAllEventsUseCase;
import com.sasa.event_service.domain.port.input.PurchaseTicketUseCase;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventController {

    private final CreateEventUseCase createEventUseCase;
    private final GetAllEventsUseCase getAllEventsUseCase;
    private final PurchaseTicketUseCase purchaseTicketUseCase;
    private final CancelTicketUseCase cancelTicketUseCase;

    public EventController(
            CreateEventUseCase createEventUseCase,
            GetAllEventsUseCase getAllEventsUseCase,
            PurchaseTicketUseCase purchaseTicketUseCase,
            CancelTicketUseCase cancelTicketUseCase
    ) {
        this.createEventUseCase = createEventUseCase;
        this.getAllEventsUseCase = getAllEventsUseCase;
        this.purchaseTicketUseCase = purchaseTicketUseCase;
        this.cancelTicketUseCase = cancelTicketUseCase;
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
        Event createdEvent = createEventUseCase.createEvent(event);
        return ResponseEntity.status(201).body(
                new ApiResponseDto<>(201, "Event created successfully", EventDtoMapper.domainToResponseDto(createdEvent))
        );
    }

    @GetMapping()
    public ResponseEntity<ApiResponseDto<PaginatedResponseDto<EventResponseDto>>> getEvents(@PageableDefault(size = 20, page = 0) Pageable pageable) {
        Page<Event> eventPage = getAllEventsUseCase.getAllEvents(pageable);
        PaginatedResponseDto<EventResponseDto> paginatedResponse =
                PaginatedResponseDto.fromPage(eventPage, EventDtoMapper::domainToResponseDto);
        return ResponseEntity.status(200).body(
                new ApiResponseDto<>(200, "Events fetched successfully", paginatedResponse)
        );
    }

    @PostMapping("/purchase-ticket")
    public ResponseEntity<Boolean> purchaseTicket(@RequestBody @Valid PurchaseTicketRequestDto dto) {
        purchaseTicketUseCase.purchaseTickets(dto.eventId(), dto.quantity());
        return ResponseEntity.ok(true);
    }

    @PostMapping("/cancel-ticket")
    public ResponseEntity<Boolean> cancelTicket(@RequestBody @Valid CancellTicketRequestDto dto) {
        cancelTicketUseCase.cancelTicket(dto.eventId(), dto.quantity());
        return ResponseEntity.ok(true);
    }
}
