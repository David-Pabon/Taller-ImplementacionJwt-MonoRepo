package upb.edu.co.fairticket.domain.port;

import upb.edu.co.fairticket.domain.model.Event;
import java.util.Optional;
import java.util.List;
import java.util.UUID;

public interface EventRepository {
    Event save(Event event);
    Optional<Event> findById(UUID id);
    List<Event> findAll();
    List<Event> findByOrganizerId(UUID organizerId);
    void deleteById(UUID id);
}
