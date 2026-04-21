package upb.edu.co.fairticket.adapter.in.rest.dto.response;

import upb.edu.co.fairticket.domain.model.Ticket;

import java.time.LocalDateTime;
import java.util.UUID;

public record TicketResponse(
    UUID id, String code, double price, String currency, String category,
    UUID purchaseId, UUID buyerId, UUID eventId, LocalDateTime purchasedAt, String status
) {
    public static TicketResponse from(Ticket ticket) {
        return new TicketResponse(ticket.getId(), ticket.getCode(), ticket.getPrice().amount(),
            ticket.getPrice().currency(), ticket.getCategory().name(), ticket.getPurchaseId(),
            ticket.getBuyerId(), ticket.getEventId(), ticket.getPurchasedAt(), ticket.getStatus().name());
    }
}
