package com.condominios.sgc.infrastructure.web.controller;

import com.condominios.sgc.application.dto.ActualizarConfiguracionRequest;
import com.condominios.sgc.application.service.ConfiguracionService;
import com.condominios.sgc.infrastructure.web.dto.ConfiguracionResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/configuracion")
public class ConfiguracionController {

    private final ConfiguracionService configuracionService;

    public ConfiguracionController(ConfiguracionService configuracionService) {
        this.configuracionService = configuracionService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ConfiguracionResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(
            ConfiguracionResponse.fromModel(configuracionService.obtener(id)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<ConfiguracionResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarConfiguracionRequest request) {
        return ResponseEntity.ok(
            ConfiguracionResponse.fromModel(configuracionService.actualizar(id, request)));
    }
}
