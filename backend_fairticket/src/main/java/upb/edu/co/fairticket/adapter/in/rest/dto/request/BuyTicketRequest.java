package upb.edu.co.fairticket.adapter.in.rest.dto.request;

import java.util.UUID;

public record BuyTicketRequest(
    UUID eventId,
    String category,
    double price,
    int quantity
) {}
