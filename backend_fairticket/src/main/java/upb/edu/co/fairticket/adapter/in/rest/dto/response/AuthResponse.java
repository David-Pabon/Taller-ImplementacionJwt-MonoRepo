package upb.edu.co.fairticket.adapter.in.rest.dto.response;

import java.util.UUID;

public record AuthResponse(String token, UUID userId, String email, String name, String role) {}