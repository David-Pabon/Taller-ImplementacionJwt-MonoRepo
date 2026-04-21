package upb.edu.co.fairticket.domain.model.valueobjects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @ParameterizedTest
    @ValueSource(strings = {
        "shakira@barranquilla.com",
        "falcao@santamarta.com",
        "carlos.vives@pescaito.co",
        "jg.balvin@medellin.com"
    })
    void testValidColombianEmails(String validEmail) {
        Email email = new Email(validEmail);
        assertEquals(validEmail, email.value());
    }

    @Test
    void testNullEmailThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Email(null));
        assertTrue(exception.getMessage().contains("Invalid email: null"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "shakirabarranquilla.com",
        "falcao@.com",
        "carlos@vives"
    })
    void testInvalidEmailsThrowsException(String invalidEmail) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Email(invalidEmail));
        assertTrue(exception.getMessage().contains("Invalid email: " + invalidEmail));
    }
}
