package com.condominios.sgc.web.controller;

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
import com.condominios.sgc.web.dto.LogPrestamoCarritoResponse;

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
            @RequestParam(required = false) Long apartamentoId,
            @RequestParam(required = false) Long condominioId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var req = new PaginacionRequest(page, size, "id", "asc", null);
        PaginacionResponse<LogPrestamoCarritoResponse> content;
        if (apartamentoId != null) {
            content = logPrestamoService.listarPorApartamento(apartamentoId, req)
                    .map(LogPrestamoCarritoResponse::fromModel);
        } else if (condominioId != null) {
            content = logPrestamoService.listarPorCondominio(condominioId, req)
                    .map(LogPrestamoCarritoResponse::fromModel);
        } else {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(content);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LogPrestamoCarritoResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(LogPrestamoCarritoResponse.fromModel(logPrestamoService.obtener(id)));
    }
}
