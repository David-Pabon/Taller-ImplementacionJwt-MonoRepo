package upb.edu.co.fairticket.domain.model;

import org.junit.jupiter.api.Test;
import upb.edu.co.fairticket.domain.model.enums.TicketCategory;
import upb.edu.co.fairticket.domain.model.enums.TicketStatus;
import upb.edu.co.fairticket.domain.model.valueobjects.Money;

import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class TicketTest {
    
    private final UUID buyerId = UUID.randomUUID();
    private final UUID eventId = UUID.randomUUID();
    private final Money price = Money.cop(10000);

    @Test
    void testCreateTicket() {
        Ticket ticket = Ticket.create("TICKET-123", price, TicketCategory.VENTA, buyerId, eventId);
        
        assertNotNull(ticket.getId());
        assertEquals("TICKET-123", ticket.getCode());
        assertEquals(price, ticket.getPrice());
        assertEquals(TicketCategory.VENTA, ticket.getCategory());
        assertEquals(buyerId, ticket.getBuyerId());
        assertEquals(eventId, ticket.getEventId());
        assertEquals(TicketStatus.PENDING, ticket.getStatus());
        assertNull(ticket.getPurchaseId());
        assertNull(ticket.getPurchasedAt());
        assertFalse(ticket.isCancelled());
        assertFalse(ticket.isPurchased());
        assertTrue(ticket.belongsTo(buyerId));
        assertFalse(ticket.belongsTo(UUID.randomUUID()));
    }

    @Test
    void testCancelTicket() {
        Ticket ticket = Ticket.create("TICKET-123", price, TicketCategory.VENTA, buyerId, eventId);
        ticket.cancel();
        
        assertEquals(TicketStatus.CANCELLED, ticket.getStatus());
        assertTrue(ticket.isCancelled());
    }

    @Test
    void testMarkPurchased() {
        Ticket ticket = Ticket.create("TICKET-123", price, TicketCategory.VENTA, buyerId, eventId);
        UUID purchaseId = UUID.randomUUID();
        
        ticket.markPurchased(purchaseId);
        
        assertEquals(TicketStatus.PURCHASED, ticket.getStatus());
        assertTrue(ticket.isPurchased());
        assertEquals(purchaseId, ticket.getPurchaseId());
        assertNotNull(ticket.getPurchasedAt());
    }
}
