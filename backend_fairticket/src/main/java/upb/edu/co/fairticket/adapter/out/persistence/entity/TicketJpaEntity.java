package upb.edu.co.fairticket.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import upb.edu.co.fairticket.domain.model.enums.TicketCategory;
import upb.edu.co.fairticket.domain.model.enums.TicketStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketJpaEntity {

    @Id
    private UUID id;

    private String code;
    private double priceAmount;
    private String priceCurrency;

    @Enumerated(EnumType.STRING)
    private TicketCategory category;

    private UUID purchaseId;
    private UUID buyerId;
    private UUID eventId;
    private LocalDateTime purchasedAt;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;
}
