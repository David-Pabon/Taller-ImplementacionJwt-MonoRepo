package upb.edu.co.fairticket.adapter.in.rest.dto.response;

import upb.edu.co.fairticket.domain.model.Event;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventResponse(
    UUID id, String name, String description, String venue, LocalDateTime eventDate,
    LocalDateTime saleStartDate, LocalDateTime saleEndDate, String category,
    int maxTicketsPerUser, int totalTickets, UUID organizerId,
    LocalDateTime createdAt, LocalDateTime updatedAt
) {
    public static EventResponse from(Event event) {
        return new EventResponse(event.getId(), event.getName(), event.getDescription(), event.getVenue(),
            event.getEventDate(), event.getSaleStartDate(), event.getSaleEndDate(), event.getCategory().name(),
            event.getMaxTicketsPerUser(), event.getTotalTickets(), event.getOrganizerId(),
            event.getCreatedAt(), event.getUpdatedAt());
    }
}
