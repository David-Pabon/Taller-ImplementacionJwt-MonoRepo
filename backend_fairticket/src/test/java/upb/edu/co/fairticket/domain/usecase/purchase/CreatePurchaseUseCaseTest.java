package upb.edu.co.fairticket.domain.usecase.purchase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import upb.edu.co.fairticket.domain.model.Purchase;
import upb.edu.co.fairticket.domain.model.Ticket;
import upb.edu.co.fairticket.domain.model.enums.TicketCategory;
import upb.edu.co.fairticket.domain.model.valueobjects.Money;
import upb.edu.co.fairticket.domain.port.PurchaseRepository;
import upb.edu.co.fairticket.domain.port.TicketRepository;
import upb.edu.co.fairticket.domain.exception.DomainException;
import upb.edu.co.fairticket.domain.exception.InvalidPurchaseException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreatePurchaseUseCaseTest {

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private CreatePurchaseUseCase createPurchaseUseCase;

    private final UUID buyerId = UUID.randomUUID();
    private final UUID eventId = UUID.randomUUID();
    private final UUID t1Id = UUID.randomUUID();
    private final UUID t2Id = UUID.randomUUID();

    @Test
    void testExecuteSuccess() {
        Ticket t1 = Ticket.create("1", Money.cop(100), TicketCategory.VENTA, buyerId, eventId);
        Ticket t2 = Ticket.create("2", Money.cop(200), TicketCategory.VENTA, buyerId, eventId);
        
        when(ticketRepository.findById(t1Id)).thenReturn(Optional.of(t1));
        when(ticketRepository.findById(t2Id)).thenReturn(Optional.of(t2));
        when(purchaseRepository.save(any(Purchase.class))).thenAnswer(i -> i.getArguments()[0]);

        Purchase purchase = createPurchaseUseCase.execute(buyerId, eventId, Arrays.asList(t1Id, t2Id));

        assertNotNull(purchase);
        assertEquals(300.0, purchase.getTotalAmount().amount());
        assertTrue(purchase.isCompleted());
        assertTrue(t1.isPurchased());
        assertTrue(t2.isPurchased());
        verify(ticketRepository, times(2)).save(any(Ticket.class));
        verify(purchaseRepository).save(any(Purchase.class));
    }

    @Test
    void testExecuteEmptyTickets() {
        assertThrows(InvalidPurchaseException.class, () -> createPurchaseUseCase.execute(buyerId, eventId, Collections.emptyList()));
    }

    @Test
    void testExecuteTicketNotFound() {
        when(ticketRepository.findById(t1Id)).thenReturn(Optional.empty());

        assertThrows(DomainException.class, () -> createPurchaseUseCase.execute(buyerId, eventId, Arrays.asList(t1Id)));
    }

    @Test
    void testExecuteTicketWrongBuyer() {
        Ticket t1 = Ticket.create("1", Money.cop(100), TicketCategory.VENTA, UUID.randomUUID(), eventId);
        when(ticketRepository.findById(t1Id)).thenReturn(Optional.of(t1));

        assertThrows(InvalidPurchaseException.class, () -> createPurchaseUseCase.execute(buyerId, eventId, Arrays.asList(t1Id)));
    }
}
