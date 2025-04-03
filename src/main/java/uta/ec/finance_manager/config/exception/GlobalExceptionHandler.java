package uta.ec.finance_manager.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        StringBuilder errorMessages = new StringBuilder();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errorMessages
                        .append(error.getDefaultMessage())
                        .append(". ")
        );

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.PRECONDITION_FAILED.value());
        response.put("message", errorMessages.toString().trim());
        response.put("error", HttpStatus.PRECONDITION_FAILED.getReasonPhrase());

        return new ResponseEntity<>(response, HttpStatus.PRECONDITION_FAILED);
    }


    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(Map.of(
                        "timestamp", java.time.LocalDateTime.now(),
                        "status", ex.getStatusCode().value(),
                        "error", ex.getStatusCode(),
                        "message", Objects.requireNonNull(ex.getReason())
                ));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                        "error", HttpStatus.UNAUTHORIZED.value(),
                        "status", HttpStatus.UNAUTHORIZED,
                        "timestamp", java.time.LocalDateTime.now(),
                        "message", (ex.getMessage().equals("Bad credentials") ? "Credenciales inválidas" : ex.getMessage())
                ));
    }
}
