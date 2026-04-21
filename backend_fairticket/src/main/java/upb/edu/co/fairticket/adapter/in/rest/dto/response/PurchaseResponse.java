package upb.edu.co.fairticket.adapter.in.rest.dto.response;

import upb.edu.co.fairticket.domain.model.Purchase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PurchaseResponse(
    UUID id, UUID buyerId, UUID eventId, List<UUID> ticketIds,
    double totalAmount, String currency, String status, LocalDateTime createdAt
) {
    public static PurchaseResponse from(Purchase purchase) {
        return new PurchaseResponse(purchase.getId(), purchase.getBuyerId(), purchase.getEventId(),
            purchase.getTicketIds(), purchase.getTotalAmount().amount(), purchase.getTotalAmount().currency(),
            purchase.getStatus().name(), purchase.getCreatedAt());
    }
}
