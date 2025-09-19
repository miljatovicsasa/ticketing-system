package com.sasa.ticket_service.port.output;

import com.sasa.ticket_service.adapter.input.security.principal.AuthPrincipal;

public interface EventServicePort {
    boolean eventCheckAndReserve(Long eventId, int quantity, AuthPrincipal principal);
    void rollbackReservation(Long eventId, int quantity, AuthPrincipal principal);

    boolean eventCancel(Long eventId, int quantity, AuthPrincipal principal);
    void rollbackCancellation(Long eventId, int quantity, AuthPrincipal principal);
}
