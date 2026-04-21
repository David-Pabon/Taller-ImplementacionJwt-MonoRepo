package upb.edu.co.fairticket.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upb.edu.co.fairticket.adapter.in.rest.dto.request.CreatePurchaseRequest;
import upb.edu.co.fairticket.adapter.in.rest.dto.response.PurchaseResponse;
import upb.edu.co.fairticket.domain.usecase.purchase.CreatePurchaseUseCase;
import upb.edu.co.fairticket.domain.usecase.purchase.CancelPurchaseUseCase;
import upb.edu.co.fairticket.domain.usecase.purchase.GetPurchaseUseCase;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final CreatePurchaseUseCase createPurchaseUseCase;
    private final CancelPurchaseUseCase cancelPurchaseUseCase;
    private final GetPurchaseUseCase getPurchaseUseCase;

    @PostMapping
    public ResponseEntity<PurchaseResponse> create(@RequestParam UUID buyerId, @RequestBody CreatePurchaseRequest request) {
        var purchase = createPurchaseUseCase.execute(buyerId, request.eventId(), request.ticketIds());
        return ResponseEntity.status(HttpStatus.CREATED).body(PurchaseResponse.from(purchase));
    }

    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<PurchaseResponse> cancel(@PathVariable UUID id, @RequestParam UUID buyerId) {
        var purchase = cancelPurchaseUseCase.execute(id, buyerId);
        return ResponseEntity.ok(PurchaseResponse.from(purchase));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseResponse> getById(@PathVariable UUID id) {
        return getPurchaseUseCase.execute(id)
            .map(p -> ResponseEntity.ok(PurchaseResponse.from(p)))
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PurchaseResponse>> getByBuyer(@RequestParam UUID buyerId) {
        return ResponseEntity.ok(
            getPurchaseUseCase.getByBuyer(buyerId).stream().map(PurchaseResponse::from).toList());
    }
}
