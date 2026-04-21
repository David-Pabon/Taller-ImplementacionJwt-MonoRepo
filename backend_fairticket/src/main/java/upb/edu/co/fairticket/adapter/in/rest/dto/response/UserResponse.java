package upb.edu.co.fairticket.adapter.in.rest.dto.response;

import upb.edu.co.fairticket.domain.model.User;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(UUID id, String name, String email, String role, LocalDateTime createdAt, LocalDateTime updatedAt) {
    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail().value(), user.getRole().name(), user.getCreatedAt(), user.getUpdatedAt());
    }
}
