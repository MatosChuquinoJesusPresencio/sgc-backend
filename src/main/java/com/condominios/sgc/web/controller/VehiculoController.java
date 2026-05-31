package com.condominios.sgc.web.controller;

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

import com.condominios.sgc.application.dto.ActualizarVehiculoRequest;
import com.condominios.sgc.application.dto.CrearVehiculoRequest;
import com.condominios.sgc.application.service.VehiculoService;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.web.dto.VehiculoResponse;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    private final VehiculoService vehiculoService;

    public VehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<VehiculoResponse> crearVehiculo(@RequestBody CrearVehiculoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(VehiculoResponse.fromModel(vehiculoService.crear(request)));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PaginacionResponse<VehiculoResponse>> listarVehiculos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var req = new PaginacionRequest(page, size, "id", "asc", null);
        PaginacionResponse<VehiculoResponse> content = vehiculoService.listar(req)
                .map(VehiculoResponse::fromModel);
        return ResponseEntity.ok(content);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<VehiculoResponse> obtenerVehiculo(@PathVariable Long id) {
        return ResponseEntity.ok(VehiculoResponse.fromModel(vehiculoService.obtener(id)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<VehiculoResponse> actualizarVehiculo(
            @PathVariable Long id,
            @RequestBody ActualizarVehiculoRequest request) {
        return ResponseEntity.ok(VehiculoResponse.fromModel(vehiculoService.actualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<Void> eliminarVehiculo(@PathVariable Long id) {
        vehiculoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
