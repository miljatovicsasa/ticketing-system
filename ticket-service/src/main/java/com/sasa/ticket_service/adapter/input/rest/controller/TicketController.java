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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Tickets", description = "Operations related to tickets")
@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketUseCasePort ticketUseCasePort;

    public TicketController(TicketUseCasePort ticketUseCasePort){
        this.ticketUseCasePort = ticketUseCasePort;
    }

    @Operation(summary = "Purchase a ticket", description = "Purchase a ticket for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ticket purchased successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping
    public ResponseEntity<ApiResponseDto<TicketResponseDto>> purchaseTicket(
            @RequestBody @Valid TicketPurchaseRequestDto ticketPurchaseRequestDto,
            @Parameter(hidden = true) @AuthenticationPrincipal AuthPrincipal principal) {

        Ticket ticket = ticketUseCasePort.purchaseTicket(
                TicketDtoMapper.requestToDomain(ticketPurchaseRequestDto),
                principal
        );

        return ResponseEntity.status(201).body(
                new ApiResponseDto<>(201, "Ticket purchased successfully", TicketDtoMapper.DomainToResponseDto(ticket))
        );
    }

    @Operation(summary = "Cancel a ticket", description = "Cancel a previously purchased ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket cancelled successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Ticket not found")
    })
    @PostMapping("/cancel")
    public ResponseEntity<ApiResponseDto<TicketResponseDto>> cancelTicket(
            @RequestBody @Valid TicketCancelRequestDto ticketCancelRequestDto,
            @Parameter(hidden = true) @AuthenticationPrincipal AuthPrincipal principal) {

        Ticket ticket = ticketUseCasePort.cancelTicket(ticketCancelRequestDto.ticketId(), principal);

        return ResponseEntity.ok(
                new ApiResponseDto<>(200, "Ticket cancelled successfully", TicketDtoMapper.DomainToResponseDto(ticket))
        );
    }

    @Operation(summary = "Get tickets", description = "Get all tickets for the authenticated user, paginated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tickets fetched successfully")
    })
    @GetMapping
    public ResponseEntity<ApiResponseDto<PaginatedResponseDto<TicketResponseDto>>> getTickets(
            @Parameter(hidden = true) @PageableDefault(size = 20, page = 0) Pageable pageable,
            @Parameter(hidden = true) @AuthenticationPrincipal AuthPrincipal principal) {

        Page<Ticket> ticketPage = ticketUseCasePort.getAllTickets(pageable, principal);

        PaginatedResponseDto<TicketResponseDto> paginatedResponse =
                PaginatedResponseDto.fromPage(ticketPage, TicketDtoMapper::DomainToResponseDto);

        return ResponseEntity.ok(
                new ApiResponseDto<>(200, "Tickets fetched successfully", paginatedResponse)
        );
    }
}
