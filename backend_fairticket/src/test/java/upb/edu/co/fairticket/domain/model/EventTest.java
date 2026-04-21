package upb.edu.co.fairticket.domain.model;

import org.junit.jupiter.api.Test;
import upb.edu.co.fairticket.domain.model.enums.EventCategory;

import java.time.LocalDateTime;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    private final UUID organizerId = UUID.randomUUID();

    @Test
    void testCreateEvent() {
        LocalDateTime eventDate = LocalDateTime.now().plusDays(30);
        LocalDateTime saleStart = LocalDateTime.now().minusDays(1);
        LocalDateTime saleEnd = LocalDateTime.now().plusDays(15);
        
        Event event = Event.create("Concierto", "Gran evento", "Estadio", eventDate, saleStart, saleEnd, 
                EventCategory.MUSICAL, 4, 1000, organizerId);
                
        assertNotNull(event.getId());
        assertEquals("Concierto", event.getName());
        assertEquals("Gran evento", event.getDescription());
        assertEquals("Estadio", event.getVenue());
        assertEquals(EventCategory.MUSICAL, event.getCategory());
        assertEquals(4, event.getMaxTicketsPerUser());
        assertEquals(1000, event.getTotalTickets());
        assertEquals(organizerId, event.getOrganizerId());
        assertNotNull(event.getCreatedAt());
        assertNotNull(event.getUpdatedAt());
        
        assertTrue(event.isWithinSalePeriod());
        assertTrue(event.isActive());
    }

    @Test
    void testIsNotWithinSalePeriodWhenPast() {
        LocalDateTime eventDate = LocalDateTime.now().plusDays(30);
        LocalDateTime saleStart = LocalDateTime.now().minusDays(10);
        LocalDateTime saleEnd = LocalDateTime.now().minusDays(1);
        
        Event event = Event.create("Concierto", "Desc", "Venue", eventDate, saleStart, saleEnd, 
                EventCategory.MUSICAL, 4, 1000, organizerId);
                
        assertFalse(event.isWithinSalePeriod());
    }
    
    @Test
    void testIsNotWithinSalePeriodWhenFuture() {
        LocalDateTime eventDate = LocalDateTime.now().plusDays(30);
        LocalDateTime saleStart = LocalDateTime.now().plusDays(1);
        LocalDateTime saleEnd = LocalDateTime.now().plusDays(15);
        
        Event event = Event.create("Concierto", "Desc", "Venue", eventDate, saleStart, saleEnd, 
                EventCategory.MUSICAL, 4, 1000, organizerId);
                
        assertFalse(event.isWithinSalePeriod());
    }

    @Test
    void testIsNotActive() {
        LocalDateTime eventDate = LocalDateTime.now().minusDays(1);
        Event event = Event.create("Past Event", "Desc", "Venue", eventDate, LocalDateTime.now().minusDays(10), 
                LocalDateTime.now().minusDays(5), EventCategory.CULTURAL, 4, 1000, organizerId);
                
        assertFalse(event.isActive());
    }

    @Test
    void testUpdateEvent() throws InterruptedException {
        Event event = Event.create("Concierto", "Desc", "Venue", LocalDateTime.now(), LocalDateTime.now(), 
                LocalDateTime.now(), EventCategory.MUSICAL, 4, 1000, organizerId);
                
        Thread.sleep(10);
        
        LocalDateTime newDate = LocalDateTime.now().plusDays(10);
        event.update("New Name", "New Desc", "New Venue", newDate, newDate, newDate, 
                EventCategory.DEPORTIVO, 2, 500);
                
        assertEquals("New Name", event.getName());
        assertEquals("New Desc", event.getDescription());
        assertEquals("New Venue", event.getVenue());
        assertEquals(newDate, event.getEventDate());
        assertEquals(EventCategory.DEPORTIVO, event.getCategory());
        assertEquals(2, event.getMaxTicketsPerUser());
        assertEquals(500, event.getTotalTickets());
        assertTrue(event.getUpdatedAt().isAfter(event.getCreatedAt()));
    }
}
