package upb.edu.co.fairticket.domain.model.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TicketCategoryTest {

    @Test
    void testDescriptions() {
        assertEquals("Pre-venta", TicketCategory.PREVENTA.getDescription());
        assertEquals("Venta Normal", TicketCategory.VENTA.getDescription());
        assertEquals("Venta Final", TicketCategory.VENTA_FINAL.getDescription());
    }
}
