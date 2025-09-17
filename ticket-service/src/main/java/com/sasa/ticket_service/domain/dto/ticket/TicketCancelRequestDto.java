package com.sasa.ticket_service.domain.dto.ticket;

public class TicketCancelRequestDto {

    private Long ticketId;

    public TicketCancelRequestDto() {}

    public TicketCancelRequestDto(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Long getTicketId() { return ticketId; }
    public void setTicketId(Long ticketId) { this.ticketId = ticketId; }
}
