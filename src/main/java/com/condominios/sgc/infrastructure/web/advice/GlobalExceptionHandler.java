package com.condominios.sgc.infrastructure.web.advice;

import com.condominios.sgc.domain.shared.exception.AutenticacionException;
import com.condominios.sgc.domain.shared.exception.DominioException;
import com.condominios.sgc.domain.shared.exception.TokenException;
import com.condominios.sgc.infrastructure.web.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AutenticacionException.class)
    public ResponseEntity<ErrorResponse> handleAutenticacion(AutenticacionException ex, HttpServletRequest request) {
        var mensaje = ex.getMessage();
        var status = mensaje != null && mensaje.contains("permiso")
                ? HttpStatus.FORBIDDEN
                : HttpStatus.UNAUTHORIZED;
        return build(status, ex.getMessage(), request);
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ErrorResponse> handleToken(TokenException ex, HttpServletRequest request) {
        return build(HttpStatus.UNAUTHORIZED, ex.getMessage(), request);
    }

    @ExceptionHandler(DominioException.class)
    public ResponseEntity<ErrorResponse> handleDominio(DominioException ex, HttpServletRequest request) {
        var mensaje = ex.getMessage();
        var status = mensaje != null && mensaje.contains("no encontrado")
                ? HttpStatus.NOT_FOUND
                : HttpStatus.BAD_REQUEST;
        return build(status, ex.getMessage(), request);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidation(HandlerMethodValidationException ex, HttpServletRequest request) {
        return build(HttpStatus.BAD_REQUEST, "parámetro inválido", request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        return build(HttpStatus.BAD_REQUEST, "tipo de parámetro inválido: " + ex.getName(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex, HttpServletRequest request) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "error interno del servidor", request);
    }

    private ResponseEntity<ErrorResponse> build(HttpStatus status, String error, HttpServletRequest request) {
        var body = new ErrorResponse(status.value(), error, request.getRequestURI());
        return new ResponseEntity<>(body, status);
    }
}
