package com.condominios.sgc.web.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.condominios.sgc.application.dto.ActualizarVehiculoRequest;
import com.condominios.sgc.application.dto.CrearVehiculoRequest;
import com.condominios.sgc.application.dto.VehiculoResponse;
import com.condominios.sgc.application.usecase.ActualizarVehiculoUseCase;
import com.condominios.sgc.application.usecase.CrearVehiculoUseCase;
import com.condominios.sgc.application.usecase.EliminarVehiculoUseCase;
import com.condominios.sgc.application.usecase.ListarVehiculosUseCase;
import com.condominios.sgc.application.usecase.ObtenerVehiculoUseCase;

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
        VehiculoResponse response = crearUseCase.crear(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<VehiculoResponse>> listarVehiculos() {
        return ResponseEntity.ok(listarUseCase.listarTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<VehiculoResponse> obtenerVehiculo(@PathVariable Long id) {
        return ResponseEntity.ok(obtenerUseCase.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<VehiculoResponse> actualizarVehiculo(
            @PathVariable Long id,
            @RequestBody ActualizarVehiculoRequest request) {
        return ResponseEntity.ok(actualizarUseCase.ejecutar(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<Void> eliminarVehiculo(@PathVariable Long id) {
        eliminarUseCase.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
