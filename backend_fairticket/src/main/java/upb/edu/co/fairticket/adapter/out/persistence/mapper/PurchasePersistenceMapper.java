package upb.edu.co.fairticket.adapter.out.persistence.mapper;

import upb.edu.co.fairticket.adapter.out.persistence.entity.PurchaseJpaEntity;
import upb.edu.co.fairticket.domain.model.Purchase;
import upb.edu.co.fairticket.domain.model.valueobjects.Money;

public class PurchasePersistenceMapper {

    private PurchasePersistenceMapper() {}

    public static Purchase toDomain(PurchaseJpaEntity entity) {
        return new Purchase(
            entity.getId(),
            entity.getBuyerId(),
            entity.getEventId(),
            entity.getTicketIds(),
            new Money(entity.getTotalAmount(), entity.getTotalCurrency()),
            entity.getStatus(),
            entity.getCreatedAt()
        );
    }

    public static PurchaseJpaEntity toJpa(Purchase purchase) {
        return new PurchaseJpaEntity(
            purchase.getId(),
            purchase.getBuyerId(),
            purchase.getEventId(),
            purchase.getTicketIds(),
            purchase.getTotalAmount().amount(),
            purchase.getTotalAmount().currency(),
            purchase.getStatus(),
            purchase.getCreatedAt()
        );
    }
}
