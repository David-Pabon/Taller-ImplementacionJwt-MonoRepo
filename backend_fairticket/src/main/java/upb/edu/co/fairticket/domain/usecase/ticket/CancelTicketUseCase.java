package upb.edu.co.fairticket.domain.usecase.ticket;

import lombok.RequiredArgsConstructor;
import upb.edu.co.fairticket.domain.model.Ticket;
import upb.edu.co.fairticket.domain.port.TicketRepository;
import upb.edu.co.fairticket.domain.exception.DomainException;

import java.util.UUID;

@RequiredArgsConstructor
public class CancelTicketUseCase {
    private final TicketRepository ticketRepository;

    public Ticket execute(UUID ticketId, UUID buyerId) {
        Ticket ticket = ticketRepository.findById(ticketId)
            .orElseThrow(() -> new DomainException("Ticket not found"));

        if (!ticket.belongsTo(buyerId)) {
            throw new DomainException("Ticket does not belong to the given user");
        }

        if (ticket.isCancelled()) {
            throw new DomainException("Ticket is already cancelled");
        }

        ticket.cancel();
        return ticketRepository.save(ticket);
    }
}
