package com.condominios.sgc.web.controller;

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

import com.condominios.sgc.application.dto.ActualizarInquilinoRequest;
import com.condominios.sgc.application.dto.CrearInquilinoRequest;
import com.condominios.sgc.application.service.InquilinoService;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.web.dto.InquilinoResponse;

@RestController
@RequestMapping("/api/inquilinos")
public class InquilinoController {

    private final InquilinoService inquilinoService;

    public InquilinoController(InquilinoService inquilinoService) {
        this.inquilinoService = inquilinoService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<InquilinoResponse> crear(@RequestBody CrearInquilinoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(InquilinoResponse.fromModel(inquilinoService.crear(request)));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PaginacionResponse<InquilinoResponse>> listar(
            @RequestParam Map<String, String> params) {
        var req = PaginacionRequest.desdeParams(params);
        Long apartamentoId = req.filtros() != null ? Long.valueOf(req.filtros().get("apartamentoId")) : null;
        if (apartamentoId == null) return ResponseEntity.badRequest().build();
        PaginacionResponse<InquilinoResponse> content = inquilinoService.listarPorApartamento(apartamentoId, req)
                .map(InquilinoResponse::fromModel);
        return ResponseEntity.ok(content);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<InquilinoResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(InquilinoResponse.fromModel(inquilinoService.obtener(id)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<InquilinoResponse> actualizar(
            @PathVariable Long id,
            @RequestBody ActualizarInquilinoRequest request) {
        return ResponseEntity.ok(InquilinoResponse.fromModel(inquilinoService.actualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        inquilinoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
