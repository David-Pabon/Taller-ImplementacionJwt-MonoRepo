package upb.edu.co.fairticket.domain.usecase.ticket;

import lombok.RequiredArgsConstructor;
import upb.edu.co.fairticket.domain.model.Ticket;
import upb.edu.co.fairticket.domain.model.Event;
import upb.edu.co.fairticket.domain.model.enums.TicketCategory;
import upb.edu.co.fairticket.domain.model.valueobjects.Money;
import upb.edu.co.fairticket.domain.port.TicketRepository;
import upb.edu.co.fairticket.domain.port.EventRepository;
import upb.edu.co.fairticket.domain.exception.DomainException;
import upb.edu.co.fairticket.domain.exception.EventNotActiveException;
import upb.edu.co.fairticket.domain.exception.EventSaleClosedException;
import upb.edu.co.fairticket.domain.exception.MaxTicketsExceededException;

import java.util.UUID;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BuyTicketUseCase {
    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;

    public List<Ticket> execute(UUID buyerId, UUID eventId, TicketCategory category, Money pricePerTicket, int quantity) {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new DomainException("Event not found"));

        if (!event.isActive()) {
            throw new EventNotActiveException("Event is not active anymore");
        }

        if (!event.isWithinSalePeriod()) {
            throw new EventSaleClosedException("Event is not within sale period");
        }

        int currentTickets = ticketRepository.countByBuyerIdAndEventId(buyerId, eventId);
        if (currentTickets + quantity > event.getMaxTicketsPerUser()) {
            throw new MaxTicketsExceededException("Cannot buy more tickets than allowed per user: " + event.getMaxTicketsPerUser());
        }

        List<Ticket> newTickets = IntStream.range(0, quantity)
            .mapToObj(i -> {
                String code = "TICKET-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
                return Ticket.create(code, pricePerTicket, category, buyerId, eventId);
            })
            .collect(Collectors.toList());

        return newTickets.stream()
            .map(ticketRepository::save)
            .collect(Collectors.toList());
    }
}
