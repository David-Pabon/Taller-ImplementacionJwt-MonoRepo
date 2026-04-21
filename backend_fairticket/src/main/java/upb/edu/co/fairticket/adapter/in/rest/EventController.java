package upb.edu.co.fairticket.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upb.edu.co.fairticket.adapter.in.rest.dto.request.CreateEventRequest;
import upb.edu.co.fairticket.adapter.in.rest.dto.response.EventResponse;
import upb.edu.co.fairticket.domain.usecase.event.CreateEventUseCase;
import upb.edu.co.fairticket.domain.usecase.event.EditEventUseCase;
import upb.edu.co.fairticket.domain.usecase.event.DeleteEventUseCase;

import java.util.UUID;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final CreateEventUseCase createEventUseCase;
    private final EditEventUseCase editEventUseCase;
    private final DeleteEventUseCase deleteEventUseCase;

    @PostMapping
    public ResponseEntity<EventResponse> create(@RequestBody CreateEventRequest request) {
        var event = createEventUseCase.execute(request.name(), request.description(), request.venue(),
            request.eventDate(), request.saleStartDate(), request.saleEndDate(), request.category(),
            request.maxTicketsPerUser(), request.totalTickets(), request.organizerId());
        return ResponseEntity.status(HttpStatus.CREATED).body(EventResponse.from(event));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> edit(@PathVariable UUID id, @RequestBody CreateEventRequest request) {
        var event = editEventUseCase.execute(id, request.name(), request.description(), request.venue(),
            request.eventDate(), request.saleStartDate(), request.saleEndDate(), request.category(),
            request.maxTicketsPerUser(), request.totalTickets());
        return ResponseEntity.ok(EventResponse.from(event));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteEventUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
