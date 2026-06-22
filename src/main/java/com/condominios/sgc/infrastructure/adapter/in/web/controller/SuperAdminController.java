package com.condominios.sgc.infrastructure.adapter.in.web.controller;

import java.util.List;

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

import com.condominios.sgc.application.dto.command.ActualizarAdministradorCommand;
import com.condominios.sgc.application.dto.command.CrearAdministradorCommand;
import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.port.in.GestionarAdministradorUseCase;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.request.ActualizarAdministradorRequest;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.request.AsignarCondominioRequest;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.request.CrearAdministradorRequest;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.request.EstadoActivoRequest;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.AdministradorResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.CondominioSimpleResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.PaginaResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.mapper.SuperAdminMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/super-admin")
@PreAuthorize("hasRole('SUPER_ADMINISTRADOR')")
public class SuperAdminController {

    private final GestionarAdministradorUseCase gestionarAdministrador;
    private final SuperAdminMapper mapper;

    public SuperAdminController(
            GestionarAdministradorUseCase gestionarAdministrador,
            SuperAdminMapper mapper) {
        this.gestionarAdministrador = gestionarAdministrador;
        this.mapper = mapper;
    }

    @GetMapping("/administrators")
    public ResponseEntity<PaginaResponse<AdministradorResponse>> listar(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean activo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        var resultado = gestionarAdministrador.listar(search, activo, new PaginaQuery(page, size));
        return ResponseEntity.ok(mapper.toPaginaResponse(resultado));
    }

    @PostMapping("/administrators")
    public ResponseEntity<AdministradorResponse> crear(
            @Valid @RequestBody CrearAdministradorRequest request) {
        var cmd = new CrearAdministradorCommand(
            request.nombres(), request.apellidos(), request.correo(),
            request.telefono(), request.contrasena());
        var resultado = gestionarAdministrador.crear(cmd);
        return ResponseEntity.ok(mapper.toAdministradorResponse(resultado));
    }

    @PutMapping("/administrators/{id}")
    public ResponseEntity<AdministradorResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarAdministradorRequest request) {
        var cmd = new ActualizarAdministradorCommand(
            request.nombres(), request.apellidos(), request.telefono());
        var resultado = gestionarAdministrador.actualizar(id, cmd);
        return ResponseEntity.ok(mapper.toAdministradorResponse(resultado));
    }

    @PatchMapping("/administrators/{id}")
    public ResponseEntity<Void> activarDesactivar(
            @PathVariable Long id,
            @Valid @RequestBody EstadoActivoRequest request) {
        gestionarAdministrador.activarDesactivar(id, request.activo());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/administrators/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        gestionarAdministrador.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/administrators/{id}/assign-condo")
    public ResponseEntity<Void> asignarCondominio(
            @PathVariable Long id,
            @RequestBody AsignarCondominioRequest request) {
        gestionarAdministrador.asignarCondominio(id, request.idCondominio());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/administrators/available")
    public ResponseEntity<List<AdministradorResponse>> listarDisponibles() {
        var resultados = gestionarAdministrador.listarDisponibles();
        return ResponseEntity.ok(mapper.toAdministradorResponses(resultados));
    }

    @GetMapping("/condominiums/unassigned")
    public ResponseEntity<List<CondominioSimpleResponse>> listarCondominiosDisponibles() {
        var resultados = gestionarAdministrador.listarCondominiosDisponibles();
        return ResponseEntity.ok(mapper.toCondominioSimpleResponses(resultados));
    }
}
