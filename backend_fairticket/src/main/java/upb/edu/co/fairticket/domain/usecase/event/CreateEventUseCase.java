package upb.edu.co.fairticket.domain.usecase.event;

import lombok.RequiredArgsConstructor;
import upb.edu.co.fairticket.domain.model.Event;
import upb.edu.co.fairticket.domain.model.enums.EventCategory;
import upb.edu.co.fairticket.domain.port.EventRepository;
import upb.edu.co.fairticket.domain.port.UserRepository;
import upb.edu.co.fairticket.domain.exception.UserNotFoundException;
import upb.edu.co.fairticket.domain.exception.DomainException;
import java.util.UUID;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class CreateEventUseCase {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public Event execute(String name, String description, String venue, LocalDateTime eventDate, 
            LocalDateTime saleStartDate, LocalDateTime saleEndDate, EventCategory category, 
            int maxTicketsPerUser, int totalTickets, UUID organizerId) {
        
        var organizer = userRepository.findById(organizerId)
            .orElseThrow(() -> new UserNotFoundException("Organizer not found: " + organizerId));
            
        if (!organizer.isOrganizer() && !organizer.isAdmin()) {
            throw new DomainException("User is not authorized to create events");
        }
        
        Event event = Event.create(name, description, venue, eventDate, saleStartDate, saleEndDate, category,
                maxTicketsPerUser, totalTickets, organizerId);
                
        return eventRepository.save(event);
    }
}
