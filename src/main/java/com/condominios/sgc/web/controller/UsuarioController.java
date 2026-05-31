package com.condominios.sgc.web.controller;

import com.condominios.sgc.application.dto.ActualizarUsuarioRequest;
import com.condominios.sgc.application.service.UsuarioService;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.web.dto.UsuarioResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import com.condominios.sgc.infrastructure.util.SecurityUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<UsuarioResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(
            UsuarioResponse.fromModel(usuarioService.obtener(id)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<PaginacionResponse<UsuarioResponse>> listar(
            @RequestParam Map<String, String> params) {
        int pagina = Integer.parseInt(params.getOrDefault("pagina", "0"));
        int tamanio = Integer.parseInt(params.getOrDefault("tamanio", "10"));
        String ordenarPor = params.get("ordenarPor");
        String direccion = params.getOrDefault("direccion", "ASC");

        Map<String, String> filtros = new HashMap<>(params);
        Set<String> keys = Set.of("pagina", "tamanio", "ordenarPor", "direccion");
        filtros.keySet().removeAll(keys);

        PaginacionRequest request = new PaginacionRequest(pagina, tamanio, ordenarPor, direccion, filtros);
        PaginacionResponse<UsuarioResponse> content = usuarioService.listar(request)
                .map(UsuarioResponse::fromModel);
        return ResponseEntity.ok(content);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<UsuarioResponse> actualizar(
            @PathVariable Long id,
            @RequestBody ActualizarUsuarioRequest request) {
        return ResponseEntity.ok(
            UsuarioResponse.fromModel(usuarioService.actualizar(id, request, SecurityUtils.obtenerRolAutenticado())));
    }

    @PatchMapping("/{id}/estado")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<UsuarioResponse> actualizarEstado(
            @PathVariable Long id,
            @RequestBody Map<String, Boolean> body) {
        return ResponseEntity.ok(
            UsuarioResponse.fromModel(usuarioService.actualizarEstado(id, body.get("activo"), SecurityUtils.obtenerRolAutenticado())));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
