package upb.edu.co.fairticket.domain.port;

import upb.edu.co.fairticket.domain.model.Ticket;
import java.util.Optional;
import java.util.List;
import java.util.UUID;

public interface TicketRepository {
    Ticket save(Ticket ticket);
    Optional<Ticket> findById(UUID id);
    List<Ticket> findByBuyerId(UUID buyerId);
    List<Ticket> findByEventId(UUID eventId);
    int countByBuyerIdAndEventId(UUID buyerId, UUID eventId);
}
