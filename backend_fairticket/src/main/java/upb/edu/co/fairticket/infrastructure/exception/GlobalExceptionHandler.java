package upb.edu.co.fairticket.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import upb.edu.co.fairticket.adapter.in.rest.dto.response.ErrorResponse;
import upb.edu.co.fairticket.domain.exception.*;

import java.nio.file.AccessDeniedException;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleDomainException(DomainException ex) {
        return switch (ex) {
            case UserNotFoundException e ->
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
            case TicketNotAvailableException e ->
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
            case EventNotActiveException e ->
                ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(e.getMessage()));
            case EventSaleClosedException e ->
                ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(e.getMessage()));
            case MaxTicketsExceededException e ->
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
            case InvalidPurchaseException e ->
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
            default ->
                ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ErrorResponse(ex.getMessage()));
        };
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity
                .status(403)
                .body(Map.of("error", "Acceso denegado", "message", ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntime(RuntimeException ex) {
        return ResponseEntity
                .status(400)
                .body(Map.of("error", ex.getMessage()));
    }
}
