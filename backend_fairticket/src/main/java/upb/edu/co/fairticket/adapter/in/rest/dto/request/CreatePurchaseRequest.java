package upb.edu.co.fairticket.adapter.in.rest.dto.request;

import java.util.List;
import java.util.UUID;

public record CreatePurchaseRequest(UUID eventId, List<UUID> ticketIds) {}
