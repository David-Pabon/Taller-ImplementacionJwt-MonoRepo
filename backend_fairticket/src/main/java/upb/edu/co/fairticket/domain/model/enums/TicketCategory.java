package upb.edu.co.fairticket.domain.model.enums;

public enum TicketCategory {
    PREVENTA("Pre-venta"),
    VENTA("Venta Normal"),
    VENTA_FINAL("Venta Final");

    private final String description;

    TicketCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
