package com.condominios.sgc.web.controller;

import com.condominios.sgc.application.dto.ActualizarConfiguracionRequest;
import com.condominios.sgc.application.usecase.ActualizarConfiguracionUseCase;
import com.condominios.sgc.application.usecase.ObtenerConfiguracionUseCase;
import com.condominios.sgc.web.dto.ConfiguracionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/configuracion")
public class ConfiguracionController {

    private final ObtenerConfiguracionUseCase obtenerConfiguracionUseCase;
    private final ActualizarConfiguracionUseCase actualizarConfiguracionUseCase;

    public ConfiguracionController(
            ObtenerConfiguracionUseCase obtenerConfiguracionUseCase,
            ActualizarConfiguracionUseCase actualizarConfiguracionUseCase) {
        this.obtenerConfiguracionUseCase = obtenerConfiguracionUseCase;
        this.actualizarConfiguracionUseCase = actualizarConfiguracionUseCase;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConfiguracionResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(
            ConfiguracionResponse.fromModel(obtenerConfiguracionUseCase.ejecutar(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConfiguracionResponse> actualizar(
            @PathVariable Long id,
            @RequestBody ActualizarConfiguracionRequest request) {
        return ResponseEntity.ok(
            ConfiguracionResponse.fromModel(actualizarConfiguracionUseCase.ejecutar(id, request)));
    }
}
