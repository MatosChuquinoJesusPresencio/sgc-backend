package com.condominios.sgc.web.controller;

import com.condominios.sgc.domain.exception.ApartamentoException;
import com.condominios.sgc.domain.exception.CarritoException;
import com.condominios.sgc.domain.exception.ConfiguracionException;
import com.condominios.sgc.domain.exception.CondominioException;
import com.condominios.sgc.domain.exception.EstacionamientoException;
import com.condominios.sgc.domain.exception.GenericoException;
import com.condominios.sgc.domain.exception.InquilinoException;
import com.condominios.sgc.domain.exception.LogAccesoVehicularException;
import com.condominios.sgc.domain.exception.LogPrestamoCarritoException;
import com.condominios.sgc.domain.exception.PisoException;
import com.condominios.sgc.domain.exception.TorreException;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.exception.VehiculoException;
import com.condominios.sgc.web.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioException.class)
    public ResponseEntity<ErrorResponse> handleUsuarioException(UsuarioException e) {
        String msg = e.getMessage();
        if (msg.contains("no existe") || msg.contains("inactivo")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.notFound(msg));
        }
        return ResponseEntity.badRequest().body(ErrorResponse.badRequest(msg));
    }

    @ExceptionHandler(ConfiguracionException.class)
    public ResponseEntity<ErrorResponse> handleConfiguracionException(ConfiguracionException e) {
        String msg = e.getMessage();
        if (msg.contains("no existe") || msg.contains("solicitada")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.notFound(msg));
        }
        return ResponseEntity.badRequest().body(ErrorResponse.badRequest(msg));
    }

    @ExceptionHandler(ApartamentoException.class)
    public ResponseEntity<ErrorResponse> handleApartamentoException(ApartamentoException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.badRequest(e.getMessage()));
    }

    @ExceptionHandler(VehiculoException.class)
    public ResponseEntity<ErrorResponse> handleVehiculoException(VehiculoException e) {
        String msg = e.getMessage();
        if (msg.contains("no existe")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.notFound(msg));
        }
        return ResponseEntity.badRequest().body(ErrorResponse.badRequest(msg));
    }

    @ExceptionHandler(InquilinoException.class)
    public ResponseEntity<ErrorResponse> handleInquilinoException(InquilinoException e) {
        String msg = e.getMessage();
        if (msg.contains("no existe")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.notFound(msg));
        }
        return ResponseEntity.badRequest().body(ErrorResponse.badRequest(msg));
    }

    @ExceptionHandler(CarritoException.class)
    public ResponseEntity<ErrorResponse> handleCarritoException(CarritoException e) {
        String msg = e.getMessage();
        if (msg.contains("no existe")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.notFound(msg));
        }
        return ResponseEntity.badRequest().body(ErrorResponse.badRequest(msg));
    }

    @ExceptionHandler(EstacionamientoException.class)
    public ResponseEntity<ErrorResponse> handleEstacionamientoException(EstacionamientoException e) {
        String msg = e.getMessage();
        if (msg.contains("no existe")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.notFound(msg));
        }
        return ResponseEntity.badRequest().body(ErrorResponse.badRequest(msg));
    }

    @ExceptionHandler(PisoException.class)
    public ResponseEntity<ErrorResponse> handlePisoException(PisoException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.badRequest(e.getMessage()));
    }

    @ExceptionHandler(TorreException.class)
    public ResponseEntity<ErrorResponse> handleTorreException(TorreException e) {
        String msg = e.getMessage();
        if (msg.contains("no existe")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.notFound(msg));
        }
        return ResponseEntity.badRequest().body(ErrorResponse.badRequest(msg));
    }

    @ExceptionHandler(CondominioException.class)
    public ResponseEntity<ErrorResponse> handleCondominioException(CondominioException e) {
        String msg = e.getMessage();
        if (msg.contains("no existe")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.notFound(msg));
        }
        return ResponseEntity.badRequest().body(ErrorResponse.badRequest(msg));
    }

    @ExceptionHandler(LogPrestamoCarritoException.class)
    public ResponseEntity<ErrorResponse> handleLogPrestamoCarritoException(LogPrestamoCarritoException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.badRequest(e.getMessage()));
    }

    @ExceptionHandler(LogAccesoVehicularException.class)
    public ResponseEntity<ErrorResponse> handleLogAccesoVehicularException(LogAccesoVehicularException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.badRequest(e.getMessage()));
    }

    @ExceptionHandler(GenericoException.class)
    public ResponseEntity<ErrorResponse> handleGenericoException(GenericoException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.badRequest(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse.of(500, "Internal Server Error", e.getMessage()));
    }
}
