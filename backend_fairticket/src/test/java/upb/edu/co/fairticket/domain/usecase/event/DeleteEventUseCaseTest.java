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
class DeleteEventUseCaseTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private DeleteEventUseCase deleteEventUseCase;

    private final UUID eventId = UUID.randomUUID();

    @Test
    void testExecuteSuccess() {
        Event event = Event.create("Ev", "Desc", "Ven", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), EventCategory.MUSICAL, 5, 100, UUID.randomUUID());
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        assertDoesNotThrow(() -> deleteEventUseCase.execute(eventId));
        verify(eventRepository).deleteById(eventId);
    }

    @Test
    void testExecuteNotFound() {
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        assertThrows(DomainException.class, () -> deleteEventUseCase.execute(eventId));
        verify(eventRepository, never()).deleteById(any());
    }
}
