package com.sasa.ticket_service.adapter.input.rest.controller;

import com.sasa.ticket_service.adapter.input.security.principal.AuthPrincipal;
import com.sasa.ticket_service.adapter.input.mapper.TicketDtoMapper;
import com.sasa.ticket_service.adapter.input.dto.response.ApiResponseDto;
import com.sasa.ticket_service.adapter.input.dto.response.PaginatedResponseDto;
import com.sasa.ticket_service.adapter.input.dto.request.TicketCancelRequestDto;
import com.sasa.ticket_service.adapter.input.dto.request.TicketPurchaseRequestDto;
import com.sasa.ticket_service.adapter.input.dto.response.TicketResponseDto;
import com.sasa.ticket_service.domain.model.Ticket;
import com.sasa.ticket_service.port.input.TicketUseCasePort;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    TicketUseCasePort ticketUseCasePort;

    public TicketController(TicketUseCasePort ticketUseCasePort){
        this.ticketUseCasePort = ticketUseCasePort;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto<TicketResponseDto>> purchaseTicket(@RequestBody @Valid TicketPurchaseRequestDto ticketPurchaseRequestDto, @AuthenticationPrincipal AuthPrincipal principal){
       Ticket ticket = ticketUseCasePort.purchaseTicket(TicketDtoMapper.requestToDomain(ticketPurchaseRequestDto), principal);
        return ResponseEntity.status(201).body(
                new ApiResponseDto<>(201, "Ticket purchased successfully", TicketDtoMapper.DomainToResponseDto(ticket))
        );
    }

    @PostMapping("/cancel")
    public ResponseEntity<ApiResponseDto<TicketResponseDto>> cancellTicket(@RequestBody @Valid TicketCancelRequestDto ticketCancelRequestDto, @AuthenticationPrincipal AuthPrincipal principal){
        Ticket ticket = ticketUseCasePort.cancelTicket(ticketCancelRequestDto.ticketId(), principal);
        return ResponseEntity.status(200).body(
                new ApiResponseDto<>(200, "Ticket cancelled successfully", TicketDtoMapper.DomainToResponseDto(ticket))
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<PaginatedResponseDto<TicketResponseDto>>> getTickets(@PageableDefault(size = 20, page = 0) Pageable pageable, @AuthenticationPrincipal AuthPrincipal principal){
        Page<Ticket> ticketPage = ticketUseCasePort.getAllTickets(pageable, principal);
        PaginatedResponseDto<TicketResponseDto> paginatedResponse =
                PaginatedResponseDto.fromPage(ticketPage, TicketDtoMapper::DomainToResponseDto);
        return ResponseEntity.status(200).body(
                new ApiResponseDto<>(200, "Tickets fetched successfully", paginatedResponse)
        );
    }
}
