package upb.edu.co.fairticket.domain.usecase.event;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import upb.edu.co.fairticket.domain.model.Event;
import upb.edu.co.fairticket.domain.model.enums.EventCategory;
import upb.edu.co.fairticket.domain.port.EventRepository;
import upb.edu.co.fairticket.domain.exception.DomainException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EditEventUseCaseTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EditEventUseCase editEventUseCase;

    private final UUID eventId = UUID.randomUUID();
    private final LocalDateTime date = LocalDateTime.now().plusDays(10);

    @Test
    void testExecuteSuccess() {
        Event event = Event.create("Ev", "Desc", "Ven", date, date, date, EventCategory.MUSICAL, 5, 100, UUID.randomUUID());
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(eventRepository.save(any(Event.class))).thenAnswer(i -> i.getArguments()[0]);

        Event updated = editEventUseCase.execute(eventId, "New", "Desc", "Ven", date, date, date, EventCategory.DEPORTIVO, 5, 100);

        assertEquals("New", updated.getName());
        assertEquals(EventCategory.DEPORTIVO, updated.getCategory());
        verify(eventRepository).save(event);
    }

    @Test
    void testExecuteNotFound() {
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        assertThrows(DomainException.class, () -> editEventUseCase.execute(eventId, "A", "B", "C", date, date, date, EventCategory.MUSICAL, 1, 1));
    }
}
