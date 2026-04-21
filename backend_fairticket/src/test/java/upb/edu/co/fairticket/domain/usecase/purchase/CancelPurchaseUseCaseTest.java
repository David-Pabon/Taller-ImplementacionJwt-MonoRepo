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

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CancelPurchaseUseCaseTest {

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private CancelPurchaseUseCase cancelPurchaseUseCase;

    private final UUID purchaseId = UUID.randomUUID();
    private final UUID buyerId = UUID.randomUUID();
    private final UUID t1Id = UUID.randomUUID();

    @Test
    void testExecuteSuccess() {
        Purchase purchase = Purchase.create(buyerId, UUID.randomUUID(), Arrays.asList(t1Id), Money.cop(100));
        Ticket t1 = Ticket.create("1", Money.cop(100), TicketCategory.VENTA, buyerId, UUID.randomUUID());
        
        when(purchaseRepository.findById(purchaseId)).thenReturn(Optional.of(purchase));
        when(ticketRepository.findById(t1Id)).thenReturn(Optional.of(t1));
        when(purchaseRepository.save(any(Purchase.class))).thenAnswer(i -> i.getArguments()[0]);

        Purchase result = cancelPurchaseUseCase.execute(purchaseId, buyerId);

        assertTrue(result.isCancelled());
        assertTrue(t1.isCancelled());
        verify(ticketRepository).save(t1);
        verify(purchaseRepository).save(purchase);
    }

    @Test
    void testExecuteNotFound() {
        when(purchaseRepository.findById(purchaseId)).thenReturn(Optional.empty());

        assertThrows(DomainException.class, () -> cancelPurchaseUseCase.execute(purchaseId, buyerId));
    }

    @Test
    void testExecuteWrongBuyer() {
        Purchase purchase = Purchase.create(UUID.randomUUID(), UUID.randomUUID(), Arrays.asList(t1Id), Money.cop(100));
        when(purchaseRepository.findById(purchaseId)).thenReturn(Optional.of(purchase));

        assertThrows(DomainException.class, () -> cancelPurchaseUseCase.execute(purchaseId, buyerId));
    }
    
    @Test
    void testExecuteAlreadyCancelled() {
        Purchase purchase = Purchase.create(buyerId, UUID.randomUUID(), Arrays.asList(t1Id), Money.cop(100));
        purchase.cancel();
        when(purchaseRepository.findById(purchaseId)).thenReturn(Optional.of(purchase));

        assertThrows(DomainException.class, () -> cancelPurchaseUseCase.execute(purchaseId, buyerId));
    }
}
