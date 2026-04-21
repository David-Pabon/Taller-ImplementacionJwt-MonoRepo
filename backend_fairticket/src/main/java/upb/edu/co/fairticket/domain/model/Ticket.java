package upb.edu.co.fairticket.domain.model;

import upb.edu.co.fairticket.domain.model.enums.TicketCategory;
import upb.edu.co.fairticket.domain.model.enums.TicketStatus;
import upb.edu.co.fairticket.domain.model.valueobjects.Money;

import lombok.Getter;
import lombok.AllArgsConstructor;

import java.util.UUID;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Ticket {
    private UUID id;
    private String code;
    private Money price;
    private TicketCategory category;
    private UUID purchaseId;
    private UUID buyerId;
    private UUID eventId;
    private LocalDateTime purchasedAt;
    private TicketStatus status;

    public static Ticket create(String code, Money price, TicketCategory category, UUID buyerId, UUID eventId) {
        return new Ticket(UUID.randomUUID(), code, price, category, null, buyerId, eventId, null, TicketStatus.PENDING);
    }

    public void cancel() {
        this.status = TicketStatus.CANCELLED;
    }

    public void markPurchased(UUID purchaseId) {
        this.status = TicketStatus.PURCHASED;
        this.purchaseId = purchaseId;
        this.purchasedAt = LocalDateTime.now();
    }

    public boolean isCancelled() {
        return this.status.equals(TicketStatus.CANCELLED);
    }

    public boolean isPurchased() {
        return this.status.equals(TicketStatus.PURCHASED);
    }

    public boolean belongsTo(UUID buyerId) {
        return this.buyerId.equals(buyerId);
    }
}
