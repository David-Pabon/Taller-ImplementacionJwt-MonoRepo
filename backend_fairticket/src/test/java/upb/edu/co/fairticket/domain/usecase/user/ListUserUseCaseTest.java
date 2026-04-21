package upb.edu.co.fairticket.domain.usecase.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import upb.edu.co.fairticket.domain.model.User;
import upb.edu.co.fairticket.domain.model.valueobjects.Email;
import upb.edu.co.fairticket.domain.port.UserRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ListUserUseCase listUserUseCase;

    @Test
    void testExecuteReturnsUsers() {
        User shakira = User.createBuyer("Shakira", new Email("shakira@barranquilla.com"));
        User carlos = User.createBuyer("Carlos Vives", new Email("carlos@santamarta.com"));
        User juanes = User.createBuyer("Juanes", new Email("juanes@medellin.com"));
        
        when(userRepository.findAll()).thenReturn(Arrays.asList(shakira, carlos, juanes));

        List<User> result = listUserUseCase.execute();

        assertEquals(3, result.size());
        assertEquals("Shakira", result.get(0).getName());
        assertEquals("Carlos Vives", result.get(1).getName());
        assertEquals("Juanes", result.get(2).getName());
        verify(userRepository).findAll();
    }

    @Test
    void testExecuteReturnsEmpty() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());
        assertTrue(listUserUseCase.execute().isEmpty());
    }
}
