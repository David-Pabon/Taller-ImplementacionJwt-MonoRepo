package upb.edu.co.fairticket.domain.usecase.purchase;

import lombok.RequiredArgsConstructor;
import upb.edu.co.fairticket.domain.model.Purchase;
import upb.edu.co.fairticket.domain.model.Ticket;
import upb.edu.co.fairticket.domain.model.valueobjects.Money;
import upb.edu.co.fairticket.domain.port.PurchaseRepository;
import upb.edu.co.fairticket.domain.port.TicketRepository;
import upb.edu.co.fairticket.domain.exception.DomainException;
import upb.edu.co.fairticket.domain.exception.InvalidPurchaseException;

import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CreatePurchaseUseCase {
    private final PurchaseRepository purchaseRepository;
    private final TicketRepository ticketRepository;

    public Purchase execute(UUID buyerId, UUID eventId, List<UUID> ticketIds) {
        if (ticketIds == null || ticketIds.isEmpty()) {
            throw new InvalidPurchaseException("Cannot create a purchase without tickets");
        }

        List<Ticket> tickets = ticketIds.stream()
            .map(id -> ticketRepository.findById(id).orElseThrow(() -> new DomainException("Ticket not found: " + id)))
            .collect(Collectors.toList());

        for (Ticket t : tickets) {
            if (!t.belongsTo(buyerId) || !t.getEventId().equals(eventId)) {
                throw new InvalidPurchaseException("All tickets must belong to the buyer and event");
            }
        }

        String currency = tickets.get(0).getPrice().currency();
        double totalAmount = tickets.stream().mapToDouble(t -> t.getPrice().amount()).sum();
        Money total = new Money(totalAmount, currency);

        Purchase purchase = Purchase.create(buyerId, eventId, ticketIds, total);
        purchase.complete(); // Assuming synchronous completion for now
        Purchase savedPurchase = purchaseRepository.save(purchase);

        for (Ticket t : tickets) {
            t.markPurchased(savedPurchase.getId());
            ticketRepository.save(t);
        }

        return savedPurchase;
    }
}
