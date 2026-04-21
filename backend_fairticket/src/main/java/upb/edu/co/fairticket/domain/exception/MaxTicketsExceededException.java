package upb.edu.co.fairticket.domain.exception;

public final class MaxTicketsExceededException extends DomainException {
    public MaxTicketsExceededException(String message) {
        super(message);
    }
}
