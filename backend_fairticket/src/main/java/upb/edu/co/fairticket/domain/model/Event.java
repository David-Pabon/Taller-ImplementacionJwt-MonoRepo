package upb.edu.co.fairticket.domain.model;

import java.util.UUID;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.AllArgsConstructor;
import upb.edu.co.fairticket.domain.model.enums.EventCategory;

@Getter
@AllArgsConstructor
public class Event {
    private UUID id;
    private String name;
    private String description;
    private String venue;
    private LocalDateTime eventDate;
    private LocalDateTime saleStartDate;
    private LocalDateTime saleEndDate;
    private EventCategory category;
    private int maxTicketsPerUser;
    private int totalTickets;
    private UUID organizerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Event create(String name, String description, String venue, LocalDateTime eventDate, 
            LocalDateTime saleStartDate, LocalDateTime saleEndDate, EventCategory category, 
            int maxTicketsPerUser, int totalTickets, UUID organizerId) {
        return new Event(UUID.randomUUID(), name, description, venue, eventDate, saleStartDate, saleEndDate, category,
                maxTicketsPerUser, totalTickets, organizerId, LocalDateTime.now(), LocalDateTime.now());
    }

    public boolean isWithinSalePeriod() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(saleStartDate) && now.isBefore(saleEndDate);
    }

    public boolean isActive() {
        return LocalDateTime.now().isBefore(eventDate);
    }

    public void update(String name, String description, String venue, LocalDateTime eventDate, 
            LocalDateTime saleStartDate, LocalDateTime saleEndDate, EventCategory category, 
            int maxTicketsPerUser, int totalTickets) {
        this.name = name;
        this.description = description;
        this.venue = venue;
        this.eventDate = eventDate;
        this.saleStartDate = saleStartDate;
        this.saleEndDate = saleEndDate;
        this.category = category;
        this.maxTicketsPerUser = maxTicketsPerUser;
        this.totalTickets = totalTickets;
        this.updatedAt = LocalDateTime.now();
    }
}
