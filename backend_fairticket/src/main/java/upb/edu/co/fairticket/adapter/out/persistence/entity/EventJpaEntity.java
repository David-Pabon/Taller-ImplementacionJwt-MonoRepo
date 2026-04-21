package upb.edu.co.fairticket.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import upb.edu.co.fairticket.domain.model.enums.EventCategory;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventJpaEntity {

    @Id
    private UUID id;

    private String name;

    @Column(length = 1000)
    private String description;

    private String venue;
    private LocalDateTime eventDate;
    private LocalDateTime saleStartDate;
    private LocalDateTime saleEndDate;

    @Enumerated(EnumType.STRING)
    private EventCategory category;

    private int maxTicketsPerUser;
    private int totalTickets;
    private UUID organizerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
