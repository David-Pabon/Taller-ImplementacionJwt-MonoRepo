package upb.edu.co.fairticket.adapter.out.persistence.mapper;

import upb.edu.co.fairticket.adapter.out.persistence.entity.TicketJpaEntity;
import upb.edu.co.fairticket.domain.model.Ticket;
import upb.edu.co.fairticket.domain.model.valueobjects.Money;

public class TicketPersistenceMapper {

    private TicketPersistenceMapper() {}

    public static Ticket toDomain(TicketJpaEntity entity) {
        return new Ticket(
            entity.getId(),
            entity.getCode(),
            new Money(entity.getPriceAmount(), entity.getPriceCurrency()),
            entity.getCategory(),
            entity.getPurchaseId(),
            entity.getBuyerId(),
            entity.getEventId(),
            entity.getPurchasedAt(),
            entity.getStatus()
        );
    }

    public static TicketJpaEntity toJpa(Ticket ticket) {
        return new TicketJpaEntity(
            ticket.getId(),
            ticket.getCode(),
            ticket.getPrice().amount(),
            ticket.getPrice().currency(),
            ticket.getCategory(),
            ticket.getPurchaseId(),
            ticket.getBuyerId(),
            ticket.getEventId(),
            ticket.getPurchasedAt(),
            ticket.getStatus()
        );
    }
}
