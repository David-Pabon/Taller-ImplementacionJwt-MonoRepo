package upb.edu.co.fairticket.domain.usecase.ticket;

import lombok.RequiredArgsConstructor;
import upb.edu.co.fairticket.domain.model.Ticket;
import upb.edu.co.fairticket.domain.port.TicketRepository;

import java.util.UUID;
import java.util.List;

@RequiredArgsConstructor
public class GetTicketsUseCase {
    private final TicketRepository ticketRepository;

    public List<Ticket> execute(UUID buyerId) {
        return ticketRepository.findByBuyerId(buyerId);
    }
}
