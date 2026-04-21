package upb.edu.co.fairticket.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upb.edu.co.fairticket.adapter.in.rest.dto.request.BuyTicketRequest;
import upb.edu.co.fairticket.adapter.in.rest.dto.response.TicketResponse;
import upb.edu.co.fairticket.domain.model.enums.TicketCategory;
import upb.edu.co.fairticket.domain.model.valueobjects.Money;
import upb.edu.co.fairticket.domain.usecase.ticket.BuyTicketUseCase;
import upb.edu.co.fairticket.domain.usecase.ticket.CancelTicketUseCase;
import upb.edu.co.fairticket.domain.usecase.ticket.GetTicketsUseCase;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final BuyTicketUseCase buyTicketUseCase;
    private final CancelTicketUseCase cancelTicketUseCase;
    private final GetTicketsUseCase getTicketsUseCase;

    @PostMapping("/buy")
    public ResponseEntity<List<TicketResponse>> buy(@RequestParam UUID buyerId, @RequestBody BuyTicketRequest request) {
        var tickets = buyTicketUseCase.execute(buyerId, request.eventId(),
            TicketCategory.valueOf(request.category().toUpperCase()), Money.cop(request.price()), request.quantity());
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(tickets.stream().map(TicketResponse::from).toList());
    }

    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<TicketResponse> cancel(@PathVariable UUID id, @RequestParam UUID buyerId) {
        var ticket = cancelTicketUseCase.execute(id, buyerId);
        return ResponseEntity.ok(TicketResponse.from(ticket));
    }

    @GetMapping
    public ResponseEntity<List<TicketResponse>> getByBuyer(@RequestParam UUID buyerId) {
        return ResponseEntity.ok(
            getTicketsUseCase.execute(buyerId).stream().map(TicketResponse::from).toList());
    }
}
