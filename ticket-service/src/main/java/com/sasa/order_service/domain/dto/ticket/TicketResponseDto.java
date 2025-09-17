package com.sasa.order_service.domain.dto.ticket;

public class TicketResponseDto {

    private Long id;
    private Long eventId;
    private Long userId;
    private int quantity;
    private String status; // ACTIVE, CANCELED, EXPIRED

    // Constructors
    public TicketResponseDto() {}

    public TicketResponseDto(Long id, Long eventId, Long userId, int quantity, String status) {
        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
        this.quantity = quantity;
        this.status = status;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

}
