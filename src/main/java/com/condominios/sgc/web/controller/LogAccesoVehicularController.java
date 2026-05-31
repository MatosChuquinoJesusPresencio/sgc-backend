package com.condominios.sgc.web.controller;

import java.util.Map;

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

import jakarta.validation.Valid;

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
            @RequestParam Map<String, String> params) {
        var req = PaginacionRequest.desdeParams(params);
        var filtros = req.filtros();
        if (filtros == null) return ResponseEntity.badRequest().build();
        if (filtros.containsKey("apartamentoId")) {
            return ResponseEntity.ok(logAccesoService.listarPorApartamento(
                    Long.valueOf(filtros.get("apartamentoId")), req)
                    .map(LogAccesoVehicularResponse::fromModel));
        } else if (filtros.containsKey("condominioId")) {
            return ResponseEntity.ok(logAccesoService.listarPorCondominio(
                    Long.valueOf(filtros.get("condominioId")), req)
                    .map(LogAccesoVehicularResponse::fromModel));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LogAccesoVehicularResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(LogAccesoVehicularResponse.fromModel(logAccesoService.obtener(id)));
    }

    @PostMapping("/entrada")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO','SEGURIDAD')")
    public ResponseEntity<LogAccesoVehicularResponse> registrarEntrada(
            @Valid @RequestBody RegistrarEntradaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(LogAccesoVehicularResponse.fromModel(logAccesoService.registrarEntrada(request)));
    }

    @PostMapping("/{id}/salida")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO','SEGURIDAD')")
    public ResponseEntity<LogAccesoVehicularResponse> registrarSalida(@PathVariable Long id) {
        return ResponseEntity.ok(LogAccesoVehicularResponse.fromModel(logAccesoService.registrarSalida(id)));
    }
}
