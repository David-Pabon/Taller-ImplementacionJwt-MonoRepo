package upb.edu.co.fairticket.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import upb.edu.co.fairticket.domain.model.enums.PurchaseStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "purchases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseJpaEntity {

    @Id
    private UUID id;

    private UUID buyerId;
    private UUID eventId;

    @ElementCollection
    @CollectionTable(name = "purchase_ticket_ids", joinColumns = @JoinColumn(name = "purchase_id"))
    @Column(name = "ticket_id")
    private List<UUID> ticketIds;

    private double totalAmount;
    private String totalCurrency;

    @Enumerated(EnumType.STRING)
    private PurchaseStatus status;

    private LocalDateTime createdAt;
}
