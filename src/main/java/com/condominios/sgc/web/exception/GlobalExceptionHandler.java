package com.condominios.sgc.web.exception;

import com.condominios.sgc.domain.exception.DominioException;
import com.condominios.sgc.web.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e ->
            errors.put(e.getField(), e.getDefaultMessage())
        );
        return ResponseEntity.badRequest()
            .body(ErrorResponse.validationError(errors));
    }

    @ExceptionHandler(DominioException.class)
    public ResponseEntity<ErrorResponse> handleDomainException(DominioException e) {
        return switch (e.getTipo()) {
            case NOT_FOUND -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.notFound(e.getMessage()));
            case BAD_REQUEST -> ResponseEntity.badRequest()
                .body(ErrorResponse.badRequest(e.getMessage()));
            case FORBIDDEN -> ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ErrorResponse.of(403, e.getMessage(), null));
        };
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(ErrorResponse.of(403, "Acceso denegado", null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse.of(500, "Error interno del servidor", null));
    }
}
