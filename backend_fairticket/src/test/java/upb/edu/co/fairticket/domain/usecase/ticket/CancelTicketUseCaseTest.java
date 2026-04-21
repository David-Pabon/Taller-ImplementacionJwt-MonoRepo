package upb.edu.co.fairticket.domain.usecase.ticket;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import upb.edu.co.fairticket.domain.model.Ticket;
import upb.edu.co.fairticket.domain.model.enums.TicketCategory;
import upb.edu.co.fairticket.domain.model.valueobjects.Money;
import upb.edu.co.fairticket.domain.port.TicketRepository;
import upb.edu.co.fairticket.domain.exception.DomainException;

import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CancelTicketUseCaseTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private CancelTicketUseCase cancelTicketUseCase;

    private final UUID ticketId = UUID.randomUUID();
    private final UUID buyerId = UUID.randomUUID();
    private final UUID otherBuyerId = UUID.randomUUID();
    private final Money price = Money.cop(100);

    @Test
    void testExecuteSuccess() {
        Ticket ticket = Ticket.create("T-1", price, TicketCategory.VENTA, buyerId, UUID.randomUUID());
        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(i -> i.getArguments()[0]);

        Ticket result = cancelTicketUseCase.execute(ticketId, buyerId);

        assertTrue(result.isCancelled());
        verify(ticketRepository).save(ticket);
    }

    @Test
    void testExecuteNotFound() {
        when(ticketRepository.findById(ticketId)).thenReturn(Optional.empty());

        assertThrows(DomainException.class, () -> cancelTicketUseCase.execute(ticketId, buyerId));
    }

    @Test
    void testExecuteNotBelongsToUser() {
        Ticket ticket = Ticket.create("T-1", price, TicketCategory.VENTA, buyerId, UUID.randomUUID());
        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));

        assertThrows(DomainException.class, () -> cancelTicketUseCase.execute(ticketId, otherBuyerId));
    }

    @Test
    void testExecuteAlreadyCancelled() {
        Ticket ticket = Ticket.create("T-1", price, TicketCategory.VENTA, buyerId, UUID.randomUUID());
        ticket.cancel();
        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));

        assertThrows(DomainException.class, () -> cancelTicketUseCase.execute(ticketId, buyerId));
    }
}
