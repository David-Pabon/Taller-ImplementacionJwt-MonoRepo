package upb.edu.co.fairticket.domain.model;

import org.junit.jupiter.api.Test;
import upb.edu.co.fairticket.domain.model.valueobjects.Money;
import upb.edu.co.fairticket.domain.model.enums.PurchaseStatus;

import java.util.UUID;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PurchaseTest {

    private final UUID buyerId = UUID.randomUUID();
    private final UUID eventId = UUID.randomUUID();
    private final List<UUID> ticketIds = Arrays.asList(UUID.randomUUID(), UUID.randomUUID());
    private final Money totalAmount = Money.cop(50000);

    @Test
    void testCreatePurchase() {
        Purchase purchase = Purchase.create(buyerId, eventId, ticketIds, totalAmount);
        
        assertNotNull(purchase.getId());
        assertEquals(buyerId, purchase.getBuyerId());
        assertEquals(eventId, purchase.getEventId());
        assertEquals(ticketIds, purchase.getTicketIds());
        assertEquals(totalAmount, purchase.getTotalAmount());
        assertEquals(PurchaseStatus.PENDING, purchase.getStatus());
        assertNotNull(purchase.getCreatedAt());
        
        assertFalse(purchase.isCancelled());
        assertFalse(purchase.isCompleted());
    }

    @Test
    void testCancelPurchase() {
        Purchase purchase = Purchase.create(buyerId, eventId, ticketIds, totalAmount);
        purchase.cancel();
        
        assertEquals(PurchaseStatus.CANCELLED, purchase.getStatus());
        assertTrue(purchase.isCancelled());
    }

    @Test
    void testCompletePurchase() {
        Purchase purchase = Purchase.create(buyerId, eventId, ticketIds, totalAmount);
        purchase.complete();
        
        assertEquals(PurchaseStatus.COMPLETED, purchase.getStatus());
        assertTrue(purchase.isCompleted());
    }
}
