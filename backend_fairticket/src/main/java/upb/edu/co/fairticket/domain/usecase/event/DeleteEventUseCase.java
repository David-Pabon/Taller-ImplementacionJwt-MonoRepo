package upb.edu.co.fairticket.domain.usecase.event;

import lombok.RequiredArgsConstructor;
import upb.edu.co.fairticket.domain.port.EventRepository;
import upb.edu.co.fairticket.domain.exception.DomainException;
import java.util.UUID;

@RequiredArgsConstructor
public class DeleteEventUseCase {
    private final EventRepository eventRepository;

    public void execute(UUID eventId) {
        eventRepository.findById(eventId)
            .orElseThrow(() -> new DomainException("Event not found: " + eventId));
            
        eventRepository.deleteById(eventId);
    }
}
