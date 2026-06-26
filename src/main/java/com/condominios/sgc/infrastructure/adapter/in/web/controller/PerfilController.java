package com.condominios.sgc.infrastructure.adapter.in.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.condominios.sgc.application.port.in.ActualizarPerfilUseCase;
import com.condominios.sgc.application.port.in.ObtenerPerfilUseCase;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.request.ActualizarPerfilRequest;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.PerfilResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.mapper.AutenticacionMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/profile")
@PreAuthorize("isAuthenticated()")
public class PerfilController {

    private final ObtenerPerfilUseCase obtenerPerfil;
    private final ActualizarPerfilUseCase actualizarPerfil;
    private final AutenticacionMapper mapper;

    public PerfilController(ObtenerPerfilUseCase obtenerPerfil,
                            ActualizarPerfilUseCase actualizarPerfil,
                            AutenticacionMapper mapper) {
        this.obtenerPerfil = obtenerPerfil;
        this.actualizarPerfil = actualizarPerfil;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<PerfilResponse> obtenerPerfil() {
        var resultado = obtenerPerfil.obtenerPerfil();
        return ResponseEntity.ok(mapper.toPerfilResponse(resultado));
    }

    @PutMapping
    public ResponseEntity<Void> actualizarPerfil(
            @Valid @RequestBody ActualizarPerfilRequest request) {
        actualizarPerfil.actualizarPerfil(request.nombres(), request.apellidos(), request.telefono());
        return ResponseEntity.ok().build();
    }
}
