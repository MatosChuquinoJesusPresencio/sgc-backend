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
import com.condominios.sgc.application.dto.command.ActualizarCondominioCommand;
import com.condominios.sgc.application.dto.command.CrearAdministradorCommand;
import com.condominios.sgc.application.dto.command.CrearCondominioCommand;
import com.condominios.sgc.application.dto.command.ForzarCambioContrasenaCommand;
import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.port.in.GestionarAdministradorUseCase;
import com.condominios.sgc.application.port.in.GestionarCondominioUseCase;
import com.condominios.sgc.application.port.in.GestionarDashboardUseCase;
import com.condominios.sgc.application.port.in.GestionarUsuariosGlobalUseCase;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.request.ActualizarAdministradorRequest;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.request.ActualizarCondominioRequest;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.request.AsignarCondominioRequest;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.request.CrearAdministradorRequest;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.request.CrearCondominioRequest;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.request.EstadoActivoRequest;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.request.ForzarCambioContrasenaRequest;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.AdministradorResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.CondominioResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.CondominioSimpleResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.DashboardMetricsResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.PaginaResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.UsuarioGlobalResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.mapper.SuperAdminMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/super-admin")
@PreAuthorize("hasRole('SUPER_ADMINISTRADOR')")
public class SuperAdminController {

    private final GestionarAdministradorUseCase gestionarAdministrador;
    private final GestionarCondominioUseCase gestionarCondominio;
    private final GestionarDashboardUseCase gestionarDashboard;
    private final GestionarUsuariosGlobalUseCase gestionarUsuariosGlobal;
    private final SuperAdminMapper mapper;

    public SuperAdminController(
            GestionarAdministradorUseCase gestionarAdministrador,
            GestionarCondominioUseCase gestionarCondominio,
            GestionarDashboardUseCase gestionarDashboard,
            GestionarUsuariosGlobalUseCase gestionarUsuariosGlobal,
            SuperAdminMapper mapper) {
        this.gestionarAdministrador = gestionarAdministrador;
        this.gestionarCondominio = gestionarCondominio;
        this.gestionarDashboard = gestionarDashboard;
        this.gestionarUsuariosGlobal = gestionarUsuariosGlobal;
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

    @GetMapping("/condominiums")
    public ResponseEntity<PaginaResponse<CondominioResponse>> listarCondominios(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean activo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        var resultado = gestionarCondominio.listar(search, activo, new PaginaQuery(page, size));
        return ResponseEntity.ok(mapper.toCondominioPaginaResponse(resultado));
    }

    @PostMapping("/condominiums")
    public ResponseEntity<CondominioResponse> crearCondominio(
            @Valid @RequestBody CrearCondominioRequest request) {
        var cmd = new CrearCondominioCommand(
            request.nombre(), request.idPais(), request.idCiudad(), request.direccion());
        var resultado = gestionarCondominio.crear(cmd);
        return ResponseEntity.ok(mapper.toCondominioResponse(resultado));
    }

    @PutMapping("/condominiums/{id}")
    public ResponseEntity<CondominioResponse> actualizarCondominio(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarCondominioRequest request) {
        var cmd = new ActualizarCondominioCommand(
            request.nombre(), request.idPais(), request.idCiudad(), request.direccion());
        var resultado = gestionarCondominio.actualizar(id, cmd);
        return ResponseEntity.ok(mapper.toCondominioResponse(resultado));
    }

    @PatchMapping("/condominiums/{id}")
    public ResponseEntity<Void> activarDesactivarCondominio(
            @PathVariable Long id,
            @Valid @RequestBody EstadoActivoRequest request) {
        gestionarCondominio.activarDesactivar(id, request.activo());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/condominiums/{id}")
    public ResponseEntity<Void> eliminarCondominio(@PathVariable Long id) {
        gestionarCondominio.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/dashboard/metrics")
    public ResponseEntity<DashboardMetricsResponse> obtenerMetricas() {
        var resultado = gestionarDashboard.obtenerMetricas();
        return ResponseEntity.ok(mapper.toDashboardMetricsResponse(resultado));
    }

    @GetMapping("/dashboard/recent-admins")
    public ResponseEntity<List<AdministradorResponse>> obtenerAdminsRecientes() {
        var resultados = gestionarDashboard.obtenerAdministradoresRecientes();
        return ResponseEntity.ok(mapper.toAdministradorResponses(resultados));
    }

    @GetMapping("/dashboard/recent-condos")
    public ResponseEntity<List<CondominioSimpleResponse>> obtenerCondominiosRecientes() {
        var resultados = gestionarDashboard.obtenerCondominiosRecientes();
        return ResponseEntity.ok(mapper.toCondominioSimpleResponses(resultados));
    }

    @GetMapping("/users")
    public ResponseEntity<PaginaResponse<UsuarioGlobalResponse>> listarUsuarios(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String rol,
            @RequestParam(required = false) Boolean activo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        var resultado = gestionarUsuariosGlobal.listar(search, rol, activo, new PaginaQuery(page, size));
        return ResponseEntity.ok(mapper.toUsuarioGlobalPaginaResponse(resultado));
    }

    @PostMapping("/users/{id}/invalidate-session")
    public ResponseEntity<Void> invalidarSesion(@PathVariable Long id) {
        gestionarUsuariosGlobal.invalidarSesion(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/{id}/force-password")
    public ResponseEntity<Void> forzarCambioContrasena(
            @PathVariable Long id,
            @Valid @RequestBody ForzarCambioContrasenaRequest request) {
        gestionarUsuariosGlobal.forzarCambioContrasena(id,
            new ForzarCambioContrasenaCommand(request.nuevaContrasena()));
        return ResponseEntity.ok().build();
    }
}
