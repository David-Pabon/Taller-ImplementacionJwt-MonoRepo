package upb.edu.co.fairticket.domain.model.valueobjects;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void testPositiveAmount() {
        Money money = new Money(50000.0, "COP");
        assertEquals(50000.0, money.amount());
        assertEquals("COP", money.currency());
    }

    @Test
    void testZeroAmount() {
        Money money = new Money(0.0, "COP");
        assertEquals(0.0, money.amount());
        assertEquals("COP", money.currency());
    }

    @Test
    void testNegativeAmountThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Money(-10.0, "COP"));
        assertEquals("Amount cannot be negative", exception.getMessage());
    }

    @Test
    void testNullCurrencyThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Money(100.0, null));
    }

    @Test
    void testBlankCurrencyThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Money(100.0, "  "));
    }

    @Test
    void testCopFactoryMethod() {
        Money money = Money.cop(25000.0);
        assertEquals(25000.0, money.amount());
        assertEquals("COP", money.currency());
    }
}
