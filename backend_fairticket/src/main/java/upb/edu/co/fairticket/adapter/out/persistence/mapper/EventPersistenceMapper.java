package upb.edu.co.fairticket.adapter.out.persistence.mapper;

import upb.edu.co.fairticket.adapter.out.persistence.entity.EventJpaEntity;
import upb.edu.co.fairticket.domain.model.Event;

public class EventPersistenceMapper {

    private EventPersistenceMapper() {}

    public static Event toDomain(EventJpaEntity entity) {
        return new Event(
            entity.getId(),
            entity.getName(),
            entity.getDescription(),
            entity.getVenue(),
            entity.getEventDate(),
            entity.getSaleStartDate(),
            entity.getSaleEndDate(),
            entity.getCategory(),
            entity.getMaxTicketsPerUser(),
            entity.getTotalTickets(),
            entity.getOrganizerId(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }

    public static EventJpaEntity toJpa(Event event) {
        return new EventJpaEntity(
            event.getId(),
            event.getName(),
            event.getDescription(),
            event.getVenue(),
            event.getEventDate(),
            event.getSaleStartDate(),
            event.getSaleEndDate(),
            event.getCategory(),
            event.getMaxTicketsPerUser(),
            event.getTotalTickets(),
            event.getOrganizerId(),
            event.getCreatedAt(),
            event.getUpdatedAt()
        );
    }
}
