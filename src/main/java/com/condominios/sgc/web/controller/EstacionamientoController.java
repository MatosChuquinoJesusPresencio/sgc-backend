package com.condominios.sgc.web.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.condominios.sgc.application.dto.ActualizarEstacionamientoRequest;
import com.condominios.sgc.application.dto.CrearEstacionamientoRequest;
import com.condominios.sgc.application.service.EstacionamientoService;
import com.condominios.sgc.domain.auxiliar.TipoVehiculo;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.web.dto.EstacionamientoResponse;

@RestController
@RequestMapping("/api/estacionamientos")
public class EstacionamientoController {

    private final EstacionamientoService estacionamientoService;

    public EstacionamientoController(EstacionamientoService estacionamientoService) {
        this.estacionamientoService = estacionamientoService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<EstacionamientoResponse> crear(@RequestBody CrearEstacionamientoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(EstacionamientoResponse.fromModel(estacionamientoService.crear(request)));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PaginacionResponse<EstacionamientoResponse>> listar(
            @RequestParam Map<String, String> params) {
        var req = PaginacionRequest.desdeParams(params);
        var filtros = req.filtros();
        if (filtros == null) return ResponseEntity.badRequest().build();
        if (filtros.containsKey("condominioId")) {
            return ResponseEntity.ok(estacionamientoService.listarPorCondominio(
                    Long.valueOf(filtros.get("condominioId")), req)
                    .map(EstacionamientoResponse::fromModel));
        } else if (filtros.containsKey("apartamentoId")) {
            return ResponseEntity.ok(estacionamientoService.listarPorApartamento(
                    Long.valueOf(filtros.get("apartamentoId")), req)
                    .map(EstacionamientoResponse::fromModel));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EstacionamientoResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(EstacionamientoResponse.fromModel(estacionamientoService.obtener(id)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<EstacionamientoResponse> actualizar(
            @PathVariable Long id,
            @RequestBody ActualizarEstacionamientoRequest request) {
        return ResponseEntity.ok(EstacionamientoResponse.fromModel(estacionamientoService.actualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        estacionamientoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/configurar")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<EstacionamientoResponse> configurar(
            @PathVariable Long id,
            @RequestBody Map<String, Object> body) {
        var tipoVehiculo = TipoVehiculo.valueOf((String) body.get("tipoVehiculo"));
        var capacidadMaxima = (Integer) body.get("capacidadMaxima");
        return ResponseEntity.ok(EstacionamientoResponse.fromModel(
            estacionamientoService.configurar(id, tipoVehiculo, capacidadMaxima)));
    }
}
