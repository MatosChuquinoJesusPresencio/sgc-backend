package com.condominios.sgc.web.controller;

import com.condominios.sgc.application.dto.ActualizarPisoRequest;
import com.condominios.sgc.application.dto.CrearPisoRequest;
import com.condominios.sgc.application.service.PisoService;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.web.dto.PisoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pisos")
public class PisoController {

    private final PisoService pisoService;

    public PisoController(PisoService pisoService) {
        this.pisoService = pisoService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<PisoResponse> crear(@RequestBody CrearPisoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(PisoResponse.fromModel(pisoService.crear(request)));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PaginacionResponse<PisoResponse>> listar(
            @RequestParam Long torreId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PaginacionRequest req = new PaginacionRequest(page, size, "id", "asc", null);
        PaginacionResponse<PisoResponse> content = pisoService.listarPorTorre(torreId, req)
                .map(PisoResponse::fromModel);
        return ResponseEntity.ok(content);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PisoResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(PisoResponse.fromModel(pisoService.obtener(id)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<PisoResponse> actualizar(
            @PathVariable Long id,
            @RequestBody ActualizarPisoRequest request) {
        return ResponseEntity.ok(PisoResponse.fromModel(pisoService.actualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pisoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
