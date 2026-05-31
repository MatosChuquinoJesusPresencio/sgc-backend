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
import com.condominios.sgc.application.usecase.ActualizarVehiculoUseCase;
import com.condominios.sgc.application.usecase.CrearVehiculoUseCase;
import com.condominios.sgc.application.usecase.EliminarVehiculoUseCase;
import com.condominios.sgc.application.usecase.ListarVehiculosUseCase;
import com.condominios.sgc.application.usecase.ObtenerVehiculoUseCase;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.web.dto.VehiculoResponse;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    private final CrearVehiculoUseCase crearUseCase;
    private final ObtenerVehiculoUseCase obtenerUseCase;
    private final ListarVehiculosUseCase listarUseCase;
    private final ActualizarVehiculoUseCase actualizarUseCase;
    private final EliminarVehiculoUseCase eliminarUseCase;

    public VehiculoController(
            CrearVehiculoUseCase crearUseCase,
            ObtenerVehiculoUseCase obtenerUseCase,
            ListarVehiculosUseCase listarUseCase,
            ActualizarVehiculoUseCase actualizarUseCase,
            EliminarVehiculoUseCase eliminarUseCase) {
        this.crearUseCase = crearUseCase;
        this.obtenerUseCase = obtenerUseCase;
        this.listarUseCase = listarUseCase;
        this.actualizarUseCase = actualizarUseCase;
        this.eliminarUseCase = eliminarUseCase;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<VehiculoResponse> crearVehiculo(@RequestBody CrearVehiculoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(VehiculoResponse.fromModel(crearUseCase.ejecutar(request)));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PaginacionResponse<VehiculoResponse>> listarVehiculos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var req = new PaginacionRequest(page, size, "id", "asc", null);
        var pageModel = listarUseCase.ejecutar(req);
        var content = pageModel.contenido().stream().map(VehiculoResponse::fromModel).toList();
        return ResponseEntity.ok(new PaginacionResponse<>(
            content, pageModel.pagina(), pageModel.tamanio(),
            pageModel.totalElementos(), pageModel.totalPaginas()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<VehiculoResponse> obtenerVehiculo(@PathVariable Long id) {
        return ResponseEntity.ok(VehiculoResponse.fromModel(obtenerUseCase.ejecutar(id)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<VehiculoResponse> actualizarVehiculo(
            @PathVariable Long id,
            @RequestBody ActualizarVehiculoRequest request) {
        return ResponseEntity.ok(VehiculoResponse.fromModel(actualizarUseCase.ejecutar(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<Void> eliminarVehiculo(@PathVariable Long id) {
        eliminarUseCase.ejecutar(id);
        return ResponseEntity.noContent().build();
    }
}
