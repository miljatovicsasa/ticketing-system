package com.sasa.event_service.adapter.input.rest.controller;

import com.sasa.event_service.adapter.input.dto.request.CancellTicketRequestDto;
import com.sasa.event_service.adapter.input.dto.request.PurchaseTicketRequestDto;
import com.sasa.event_service.port.input.EventUseCasePort;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/internal/events")
public class IntrenalEventController {

    private final EventUseCasePort eventUseCasePort;

    public IntrenalEventController(EventUseCasePort eventUseCasePort) {
        this.eventUseCasePort = eventUseCasePort;
    }

    @PostMapping("/purchase-ticket")
    public ResponseEntity<Boolean> purchaseTicket(@RequestBody @Valid PurchaseTicketRequestDto dto) {
        eventUseCasePort.purchaseTickets(dto.eventId(), dto.quantity());
        return ResponseEntity.ok(true);
    }

    @PostMapping("/rollback-purchase-ticket")
    public ResponseEntity<Boolean> rollbackPurchaseTicket(@RequestBody @Valid PurchaseTicketRequestDto dto) {
        eventUseCasePort.rollbackPurchaseTicket(dto.eventId(), dto.quantity());
        return ResponseEntity.ok(true);
    }

    @PostMapping("/cancel-ticket")
    public ResponseEntity<Boolean> cancelTicket(@RequestBody @Valid CancellTicketRequestDto dto) {
        eventUseCasePort.cancelTicket(dto.eventId(), dto.quantity());
        return ResponseEntity.ok(true);
    }

    @PostMapping("/rollback-cancel-ticket")
    public ResponseEntity<Boolean> rollbackCancelTicket(@RequestBody @Valid CancellTicketRequestDto dto) {
        eventUseCasePort.rollbackCancelTicket(dto.eventId(), dto.quantity());
        return ResponseEntity.ok(true);
    }
}
