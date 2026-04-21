package upb.edu.co.fairticket.domain.usecase.ticket;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import upb.edu.co.fairticket.domain.model.Event;
import upb.edu.co.fairticket.domain.model.Ticket;
import upb.edu.co.fairticket.domain.model.enums.EventCategory;
import upb.edu.co.fairticket.domain.model.enums.TicketCategory;
import upb.edu.co.fairticket.domain.model.valueobjects.Money;
import upb.edu.co.fairticket.domain.port.EventRepository;
import upb.edu.co.fairticket.domain.port.TicketRepository;
import upb.edu.co.fairticket.domain.exception.DomainException;
import upb.edu.co.fairticket.domain.exception.EventNotActiveException;
import upb.edu.co.fairticket.domain.exception.EventSaleClosedException;
import upb.edu.co.fairticket.domain.exception.MaxTicketsExceededException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuyTicketUseCaseTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private BuyTicketUseCase buyTicketUseCase;

    private final UUID buyerId = UUID.randomUUID();
    private final UUID eventId = UUID.randomUUID();
    private final Money price = Money.cop(100);

    @Test
    void testExecuteSuccess() {
        Event event = Event.create("Ev", "Desc", "Ven", LocalDateTime.now().plusDays(10), 
                LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(5), EventCategory.MUSICAL, 5, 100, UUID.randomUUID());
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(ticketRepository.countByBuyerIdAndEventId(buyerId, eventId)).thenReturn(0);
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(i -> i.getArguments()[0]);

        List<Ticket> tickets = buyTicketUseCase.execute(buyerId, eventId, TicketCategory.VENTA, price, 2);

        assertEquals(2, tickets.size());
        verify(ticketRepository, times(2)).save(any(Ticket.class));
    }

    @Test
    void testExecuteEventNotFound() {
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        assertThrows(DomainException.class, () -> buyTicketUseCase.execute(buyerId, eventId, TicketCategory.VENTA, price, 1));
    }

    @Test
    void testExecuteEventNotActive() {
        Event event = Event.create("Ev", "Desc", "Ven", LocalDateTime.now().minusDays(10), 
                LocalDateTime.now().minusDays(20), LocalDateTime.now().minusDays(15), EventCategory.MUSICAL, 5, 100, UUID.randomUUID());
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        assertThrows(EventNotActiveException.class, () -> buyTicketUseCase.execute(buyerId, eventId, TicketCategory.VENTA, price, 1));
    }

    @Test
    void testExecuteEventSaleClosed() {
        Event event = Event.create("Ev", "Desc", "Ven", LocalDateTime.now().plusDays(10), 
                LocalDateTime.now().minusDays(20), LocalDateTime.now().minusDays(15), EventCategory.MUSICAL, 5, 100, UUID.randomUUID());
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        assertThrows(EventSaleClosedException.class, () -> buyTicketUseCase.execute(buyerId, eventId, TicketCategory.VENTA, price, 1));
    }

    @Test
    void testExecuteMaxTicketsExceeded() {
        Event event = Event.create("Ev", "Desc", "Ven", LocalDateTime.now().plusDays(10), 
                LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(5), EventCategory.MUSICAL, 2, 100, UUID.randomUUID());
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(ticketRepository.countByBuyerIdAndEventId(buyerId, eventId)).thenReturn(1);

        assertThrows(MaxTicketsExceededException.class, () -> buyTicketUseCase.execute(buyerId, eventId, TicketCategory.VENTA, price, 2));
    }
}
