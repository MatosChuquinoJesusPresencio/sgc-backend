package com.condominios.sgc.web.controller;

import java.util.Map;

import com.condominios.sgc.application.dto.ActualizarApartamentoRequest;
import com.condominios.sgc.application.dto.CrearApartamentoRequest;
import com.condominios.sgc.application.service.ApartamentoService;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.web.dto.ApartamentoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/apartamentos")
public class ApartamentoController {

    private final ApartamentoService apartamentoService;

    public ApartamentoController(ApartamentoService apartamentoService) {
        this.apartamentoService = apartamentoService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<ApartamentoResponse> crear(@RequestBody CrearApartamentoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApartamentoResponse.fromModel(apartamentoService.crear(request)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<PaginacionResponse<ApartamentoResponse>> listar(
            @RequestParam Map<String, String> params) {
        var req = PaginacionRequest.desdeParams(params);
        var filtros = req.filtros();
        if (filtros == null || !filtros.containsKey("pisoId")) return ResponseEntity.badRequest().build();
        Long pisoId = Long.valueOf(filtros.get("pisoId"));
        PaginacionResponse<ApartamentoResponse> content = apartamentoService.listarPorPiso(pisoId, req)
                .map(ApartamentoResponse::fromModel);
        return ResponseEntity.ok(content);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApartamentoResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(ApartamentoResponse.fromModel(apartamentoService.obtener(id)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<ApartamentoResponse> actualizar(
            @PathVariable Long id,
            @RequestBody ActualizarApartamentoRequest request) {
        return ResponseEntity.ok(ApartamentoResponse.fromModel(apartamentoService.actualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        apartamentoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
