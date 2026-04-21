package upb.edu.co.fairticket.domain.model;

import lombok.Getter;
import lombok.AllArgsConstructor;
import java.util.UUID;
import java.util.List;
import java.time.LocalDateTime;

import upb.edu.co.fairticket.domain.model.enums.PurchaseStatus;
import upb.edu.co.fairticket.domain.model.valueobjects.Money;

@Getter
@AllArgsConstructor
public class Purchase {
    private UUID id;
    private UUID buyerId;
    private UUID eventId;
    private List<UUID> ticketIds;
    private Money totalAmount;
    private PurchaseStatus status;
    private LocalDateTime createdAt;

    public static Purchase create(UUID buyerId, UUID eventId, List<UUID> ticketIds, Money totalAmount) {
        return new Purchase(UUID.randomUUID(), buyerId, eventId, ticketIds, totalAmount, PurchaseStatus.PENDING, LocalDateTime.now());
    }

    public void cancel() {
        this.status = PurchaseStatus.CANCELLED;
    }

    public void complete() {
        this.status = PurchaseStatus.COMPLETED;
    }

    public boolean isCancelled() {
        return this.status.equals(PurchaseStatus.CANCELLED);
    }

    public boolean isCompleted() {
        return this.status.equals(PurchaseStatus.COMPLETED);
    }
}
