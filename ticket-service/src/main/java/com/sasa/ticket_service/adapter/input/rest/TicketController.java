package com.sasa.ticket_service.adapter.input.rest;

import com.sasa.ticket_service.adapter.input.mapper.TicketDtoMapper;
import com.sasa.ticket_service.domain.dto.common.ApiResponseDto;
import com.sasa.ticket_service.domain.dto.common.PaginatedResponseDto;
import com.sasa.ticket_service.domain.dto.ticket.TicketCancelRequestDto;
import com.sasa.ticket_service.domain.dto.ticket.TicketPurchaseRequestDto;
import com.sasa.ticket_service.domain.dto.ticket.TicketResponseDto;
import com.sasa.ticket_service.domain.model.Ticket;
import com.sasa.ticket_service.domain.port.input.CancellTicketUseCase;
import com.sasa.ticket_service.domain.port.input.GetAllTicketsUseCase;
import com.sasa.ticket_service.domain.port.input.PurchaseTicketUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final PurchaseTicketUseCase purchaseTicketUseCase;
    private final GetAllTicketsUseCase getAllTicketsUseCase;
    private final CancellTicketUseCase cancellTicketUseCase;

    public TicketController(PurchaseTicketUseCase purchaseTicketUseCase, GetAllTicketsUseCase getAllTicketsUseCase, CancellTicketUseCase cancellTicketUseCase ){
        this.purchaseTicketUseCase = purchaseTicketUseCase;
        this.getAllTicketsUseCase = getAllTicketsUseCase;
        this.cancellTicketUseCase = cancellTicketUseCase;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto<TicketResponseDto>> purchaseTicket(@RequestBody TicketPurchaseRequestDto ticketPurchaseRequestDto){
       Ticket ticket = purchaseTicketUseCase.purchaseTicket(TicketDtoMapper.requestToDomain(ticketPurchaseRequestDto));
        return ResponseEntity.status(201).body(
                new ApiResponseDto<>(201, "Ticket purchased successfully", TicketDtoMapper.DomainToResponseDto(ticket))
        );
    }

    @PatchMapping
    public ResponseEntity<ApiResponseDto<TicketResponseDto>> cancellTicket(@RequestBody TicketCancelRequestDto ticketCancelRequestDto){
        Ticket ticket = cancellTicketUseCase.cancellTicket(ticketCancelRequestDto.getTicketId());
        return ResponseEntity.status(200).body(
                new ApiResponseDto<>(200, "Ticket cancelled successfully", TicketDtoMapper.DomainToResponseDto(ticket))
        );
    }

    @GetMapping
    public ResponseEntity<PaginatedResponseDto<TicketResponseDto>> getTickets(@PageableDefault(size = 20, page = 0) Pageable pageable){
        Page<Ticket> ticketPage = getAllTicketsUseCase.getAllTickets(pageable);
        PaginatedResponseDto<TicketResponseDto> paginatedResponse =
                PaginatedResponseDto.fromPage(ticketPage, TicketDtoMapper::DomainToResponseDto);
        return ResponseEntity.ok(paginatedResponse);
    }

}
