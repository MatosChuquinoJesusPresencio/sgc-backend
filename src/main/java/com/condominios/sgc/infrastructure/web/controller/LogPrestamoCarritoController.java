package com.condominios.sgc.infrastructure.web.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.condominios.sgc.application.service.LogPrestamoCarritoService;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.infrastructure.web.dto.LogPrestamoCarritoResponse;

@RestController
@RequestMapping("/api/logs-prestamo")
public class LogPrestamoCarritoController {

    private final LogPrestamoCarritoService logPrestamoService;

    public LogPrestamoCarritoController(LogPrestamoCarritoService logPrestamoService) {
        this.logPrestamoService = logPrestamoService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PaginacionResponse<LogPrestamoCarritoResponse>> listar(
            @RequestParam Map<String, String> params) {
        var req = PaginacionRequest.desdeParams(params);
        var filtros = req.filtros();
        if (filtros == null) return ResponseEntity.badRequest().build();
        if (filtros.containsKey("apartamentoId")) {
            return ResponseEntity.ok(logPrestamoService.listarPorApartamento(
                    Long.valueOf(filtros.get("apartamentoId")), req)
                    .map(LogPrestamoCarritoResponse::fromModel));
        } else if (filtros.containsKey("condominioId")) {
            return ResponseEntity.ok(logPrestamoService.listarPorCondominio(
                    Long.valueOf(filtros.get("condominioId")), req)
                    .map(LogPrestamoCarritoResponse::fromModel));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LogPrestamoCarritoResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(LogPrestamoCarritoResponse.fromModel(logPrestamoService.obtener(id)));
    }
}
