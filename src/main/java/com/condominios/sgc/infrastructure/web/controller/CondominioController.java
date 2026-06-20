package com.condominios.sgc.infrastructure.web.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.condominios.sgc.application.dto.ActualizarCondominioRequest;
import com.condominios.sgc.application.dto.CrearCondominioRequest;
import com.condominios.sgc.application.service.CondominioService;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.infrastructure.web.dto.CondominioRelationsResponse;
import com.condominios.sgc.infrastructure.web.dto.CondominioResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/condominios")
public class CondominioController {

    private final CondominioService condominioService;

    public CondominioController(CondominioService condominioService) {
        this.condominioService = condominioService;
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMINISTRADOR')")
    public ResponseEntity<CondominioResponse> crear(@Valid @RequestBody CrearCondominioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(CondominioResponse.fromModel(condominioService.crear(request)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CondominioResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(
            CondominioResponse.fromModel(condominioService.obtener(id)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<PaginacionResponse<CondominioResponse>> listar(
            @RequestParam Map<String, String> params) {
        PaginacionRequest request = PaginacionRequest.desdeParams(params);
        PaginacionResponse<CondominioResponse> content = condominioService.listar(request)
                .map(CondominioResponse::fromModel);
        return ResponseEntity.ok(content);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<CondominioResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarCondominioRequest request) {
        return ResponseEntity.ok(
            CondominioResponse.fromModel(condominioService.actualizar(id, request)));
    }

    @GetMapping("/{id}/relations")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<CondominioRelationsResponse> obtenerRelations(@PathVariable Long id) {
        return ResponseEntity.ok(condominioService.obtenerRelations(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMINISTRADOR')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        condominioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
