package com.condominios.sgc.web.exception;

import com.condominios.sgc.domain.exception.CorreoException;
import com.condominios.sgc.domain.exception.DominioException;
import com.condominios.sgc.domain.exception.TokenException;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.web.dto.response.ErrorResponse;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioException.class)
    public ResponseEntity<ErrorResponse> handleUsuario(UsuarioException ex) {
        String msg = ex.getMessage();
        if (msg.contains("Credenciales"))
            return error(401, "No autorizado", msg);
        if (msg.contains("no encontrado"))
            return error(404, "No encontrado", msg);
        return error(400, "Solicitud inválida", msg);
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ErrorResponse> handleToken(TokenException ex) {
        String msg = ex.getMessage();
        if (msg.contains("expirado") || msg.contains("ya usado") || msg.contains("no encontrado"))
            return error(401, "No autorizado", msg);
        return error(400, "Solicitud inválida", msg);
    }

    @ExceptionHandler(CorreoException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleCorreo(CorreoException ex) {
        return new ErrorResponse(500, "Error interno", ex.getMessage());
    }

    @ExceptionHandler(DominioException.class)
    public ResponseEntity<ErrorResponse> handleDominio(DominioException ex) {
        String msg = ex.getMessage();
        if (msg.contains("no encontrado"))
            return error(404, "No encontrado", msg);
        return error(400, "Solicitud inválida", msg);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return new ErrorResponse(400, "Error de validación", msg);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraint(ConstraintViolationException ex) {
        return new ErrorResponse(400, "Error de validación", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneral(Exception ex) {
        return new ErrorResponse(500, "Error interno", "Ocurrió un error inesperado");
    }

    private ResponseEntity<ErrorResponse> error(int status, String error, String message) {
        return ResponseEntity.status(status)
                .body(new ErrorResponse(status, error, message));
    }
}
