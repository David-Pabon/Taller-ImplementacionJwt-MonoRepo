package upb.edu.co.fairticket.domain.exception;

public sealed class DomainException extends RuntimeException
    permits TicketNotAvailableException, MaxTicketsExceededException,
            EventNotActiveException, EventSaleClosedException,
            UserNotFoundException, InvalidPurchaseException {
    
    public DomainException(String message) {
        super(message);
    }
}
