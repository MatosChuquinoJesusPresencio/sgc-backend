package com.condominios.sgc.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.condominios.sgc.application.dto.RegistrarEntradaRequest;
import com.condominios.sgc.application.service.LogAccesoVehicularService;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.web.dto.LogAccesoVehicularResponse;

@RestController
@RequestMapping("/api/logs-acceso")
public class LogAccesoVehicularController {

    private final LogAccesoVehicularService logAccesoService;

    public LogAccesoVehicularController(LogAccesoVehicularService logAccesoService) {
        this.logAccesoService = logAccesoService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PaginacionResponse<LogAccesoVehicularResponse>> listar(
            @RequestParam(required = false) Long apartamentoId,
            @RequestParam(required = false) Long condominioId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var req = new PaginacionRequest(page, size, "id", "asc", null);
        PaginacionResponse<LogAccesoVehicularResponse> content;
        if (apartamentoId != null) {
            content = logAccesoService.listarPorApartamento(apartamentoId, req)
                    .map(LogAccesoVehicularResponse::fromModel);
        } else if (condominioId != null) {
            content = logAccesoService.listarPorCondominio(condominioId, req)
                    .map(LogAccesoVehicularResponse::fromModel);
        } else {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(content);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LogAccesoVehicularResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(LogAccesoVehicularResponse.fromModel(logAccesoService.obtener(id)));
    }

    @PostMapping("/entrada")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO','SEGURIDAD')")
    public ResponseEntity<LogAccesoVehicularResponse> registrarEntrada(
            @RequestBody RegistrarEntradaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(LogAccesoVehicularResponse.fromModel(logAccesoService.registrarEntrada(request)));
    }

    @PostMapping("/{id}/salida")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO','SEGURIDAD')")
    public ResponseEntity<LogAccesoVehicularResponse> registrarSalida(@PathVariable Long id) {
        return ResponseEntity.ok(LogAccesoVehicularResponse.fromModel(logAccesoService.registrarSalida(id)));
    }
}
