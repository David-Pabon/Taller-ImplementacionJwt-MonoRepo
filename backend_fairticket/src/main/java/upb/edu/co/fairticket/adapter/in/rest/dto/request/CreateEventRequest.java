package upb.edu.co.fairticket.adapter.in.rest.dto.request;

import upb.edu.co.fairticket.domain.model.enums.EventCategory;
import java.time.LocalDateTime;
import java.util.UUID;

public record CreateEventRequest(
    String name,
    String description,
    String venue,
    LocalDateTime eventDate,
    LocalDateTime saleStartDate,
    LocalDateTime saleEndDate,
    EventCategory category,
    int maxTicketsPerUser,
    int totalTickets,
    UUID organizerId
) {}
