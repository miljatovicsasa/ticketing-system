package com.sasa.ticket_service.adapter.input.rest.controller;

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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    TicketUseCasePort ticketUseCasePort;

    public TicketController(TicketUseCasePort ticketUseCasePort){
        this.ticketUseCasePort = ticketUseCasePort;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto<TicketResponseDto>> purchaseTicket(@RequestBody @Valid TicketPurchaseRequestDto ticketPurchaseRequestDto){
       Ticket ticket = ticketUseCasePort.purchaseTicket(TicketDtoMapper.requestToDomain(ticketPurchaseRequestDto));
        return ResponseEntity.status(201).body(
                new ApiResponseDto<>(201, "Ticket purchased successfully", TicketDtoMapper.DomainToResponseDto(ticket))
        );
    }

    @PostMapping("/cancel")
    public ResponseEntity<ApiResponseDto<TicketResponseDto>> cancellTicket(@RequestBody @Valid TicketCancelRequestDto ticketCancelRequestDto){
        Ticket ticket = ticketUseCasePort.cancellTicket(ticketCancelRequestDto.ticketId());
        return ResponseEntity.status(200).body(
                new ApiResponseDto<>(200, "Ticket cancelled successfully", TicketDtoMapper.DomainToResponseDto(ticket))
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<PaginatedResponseDto<TicketResponseDto>>> getTickets(@PageableDefault(size = 20, page = 0) Pageable pageable){
        Page<Ticket> ticketPage = ticketUseCasePort.getAllTickets(pageable);
        PaginatedResponseDto<TicketResponseDto> paginatedResponse =
                PaginatedResponseDto.fromPage(ticketPage, TicketDtoMapper::DomainToResponseDto);
        return ResponseEntity.status(200).body(
                new ApiResponseDto<>(200, "Tickets fetched successfully", paginatedResponse)
        );
    }
}
