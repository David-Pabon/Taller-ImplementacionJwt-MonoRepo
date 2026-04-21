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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetTicketsUseCaseTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private GetTicketsUseCase getTicketsUseCase;

    private final UUID buyerId = UUID.randomUUID();

    @Test
    void testExecuteReturnsTickets() {
        Ticket t1 = Ticket.create("1", Money.cop(100), TicketCategory.VENTA, buyerId, UUID.randomUUID());
        Ticket t2 = Ticket.create("2", Money.cop(100), TicketCategory.VENTA, buyerId, UUID.randomUUID());
        when(ticketRepository.findByBuyerId(buyerId)).thenReturn(Arrays.asList(t1, t2));

        List<Ticket> result = getTicketsUseCase.execute(buyerId);

        assertEquals(2, result.size());
        verify(ticketRepository).findByBuyerId(buyerId);
    }

    @Test
    void testExecuteEmpty() {
        when(ticketRepository.findByBuyerId(buyerId)).thenReturn(Collections.emptyList());
        assertTrue(getTicketsUseCase.execute(buyerId).isEmpty());
    }
}
