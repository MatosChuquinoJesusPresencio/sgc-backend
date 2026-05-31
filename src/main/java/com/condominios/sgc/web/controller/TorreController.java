package com.condominios.sgc.web.controller;

import com.condominios.sgc.application.dto.ActualizarTorreRequest;
import com.condominios.sgc.application.dto.CrearTorreRequest;
import com.condominios.sgc.application.service.TorreService;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.web.dto.TorreResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/torres")
public class TorreController {

    private final TorreService torreService;

    public TorreController(TorreService torreService) {
        this.torreService = torreService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<TorreResponse> crear(@RequestBody CrearTorreRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(TorreResponse.fromModel(torreService.crear(request)));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PaginacionResponse<TorreResponse>> listar(
            @RequestParam Long condominioId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PaginacionRequest req = new PaginacionRequest(page, size, "id", "asc", null);
        PaginacionResponse<TorreResponse> content = torreService.listarPorCondominio(condominioId, req)
                .map(TorreResponse::fromModel);
        return ResponseEntity.ok(content);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TorreResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(TorreResponse.fromModel(torreService.obtener(id)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<TorreResponse> actualizar(
            @PathVariable Long id,
            @RequestBody ActualizarTorreRequest request) {
        return ResponseEntity.ok(TorreResponse.fromModel(torreService.actualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        torreService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
