package upb.edu.co.fairticket.domain.usecase.event;

import lombok.RequiredArgsConstructor;
import upb.edu.co.fairticket.domain.model.Event;
import upb.edu.co.fairticket.domain.model.enums.EventCategory;
import upb.edu.co.fairticket.domain.port.EventRepository;
import upb.edu.co.fairticket.domain.exception.DomainException;
import java.util.UUID;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class EditEventUseCase {
    private final EventRepository eventRepository;

    public Event execute(UUID eventId, String name, String description, String venue, LocalDateTime eventDate, 
            LocalDateTime saleStartDate, LocalDateTime saleEndDate, EventCategory category, 
            int maxTicketsPerUser, int totalTickets) {
            
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new DomainException("Event not found: " + eventId));
            
        event.update(name, description, venue, eventDate, saleStartDate, saleEndDate, category,
                maxTicketsPerUser, totalTickets);
                
        return eventRepository.save(event);
    }
}
