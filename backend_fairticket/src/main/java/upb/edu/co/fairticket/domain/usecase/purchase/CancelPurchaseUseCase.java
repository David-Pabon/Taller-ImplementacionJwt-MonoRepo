package upb.edu.co.fairticket.domain.usecase.purchase;

import lombok.RequiredArgsConstructor;
import upb.edu.co.fairticket.domain.model.Purchase;
import upb.edu.co.fairticket.domain.port.PurchaseRepository;
import upb.edu.co.fairticket.domain.port.TicketRepository;
import upb.edu.co.fairticket.domain.exception.DomainException;

import java.util.UUID;

@RequiredArgsConstructor
public class CancelPurchaseUseCase {
    private final PurchaseRepository purchaseRepository;
    private final TicketRepository ticketRepository;

    public Purchase execute(UUID purchaseId, UUID buyerId) {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new DomainException("Purchase not found: " + purchaseId));

        if (!purchase.getBuyerId().equals(buyerId)) {
            throw new DomainException("Purchase does not belong to the given user");
        }

        if (purchase.isCancelled()) {
            throw new DomainException("Purchase is already cancelled");
        }

        purchase.cancel();

        for (UUID ticketId : purchase.getTicketIds()) {
            ticketRepository.findById(ticketId).ifPresent(ticket -> {
                ticket.cancel();
                ticketRepository.save(ticket);
            });
        }

        return purchaseRepository.save(purchase);
    }
}
