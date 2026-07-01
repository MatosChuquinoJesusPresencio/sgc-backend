package com.condominios.sgc.infrastructure.adapter.in.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.condominios.sgc.application.dto.command.ActualizarAdminUserCommand;
import com.condominios.sgc.application.dto.command.ActualizarConfiguracionCommand;
import com.condominios.sgc.application.dto.command.ActualizarMiCondominioCommand;
import com.condominios.sgc.application.dto.command.ActualizarOcupantesCommand;
import com.condominios.sgc.application.dto.command.ActualizarStatusAssetCommand;
import com.condominios.sgc.application.dto.command.AsignarParkingCommand;
import com.condominios.sgc.application.dto.command.AsignarPropietarioCommand;
import com.condominios.sgc.application.dto.command.CrearAdminUserCommand;
import com.condominios.sgc.application.dto.command.CrearAssetCommand;
import com.condominios.sgc.application.dto.command.CrearNodeCommand;
import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.port.in.GestionarAdminActivosUseCase;
import com.condominios.sgc.application.port.in.GestionarAdminApartamentosUseCase;
import com.condominios.sgc.application.port.in.GestionarAdminCondominioUseCase;
import com.condominios.sgc.application.port.in.GestionarAdminDashboardUseCase;
import com.condominios.sgc.application.port.in.GestionarAdminEstructuraUseCase;
import com.condominios.sgc.application.port.in.GestionarAdminLogsUseCase;
import com.condominios.sgc.application.port.in.GestionarAdminUsuariosUseCase;
import com.condominios.sgc.domain.type.TipoDocumento;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.request.ActualizarAdminUserRequest;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.request.ActualizarConfiguracionRequest;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.request.ActualizarMiCondominioRequest;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.request.ActualizarOcupantesRequest;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.request.ActualizarStatusAssetRequest;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.request.AsignarParkingRequest;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.request.AsignarPropietarioRequest;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.request.CrearAdminUserRequest;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.request.CrearAssetRequest;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.request.CrearNodeRequest;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.request.EstadoActivoRequest;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.AdminApartamentoDetailResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.AdminAssetResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.AdminLogEntryResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.AdminUserResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.AdminCondominioInfoResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.AdminConfiguracionResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.AdminDashboardMetricsResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.AdminStructureResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.PaginaResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.mapper.AdminCondominioMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMINISTRADOR_CONDOMINIO')")
public class AdminCondominioController {

    private final GestionarAdminDashboardUseCase gestionarAdminDashboard;
    private final GestionarAdminCondominioUseCase gestionarAdminCondominio;
    private final GestionarAdminEstructuraUseCase gestionarAdminEstructura;
    private final GestionarAdminUsuariosUseCase gestionarAdminUsuarios;
    private final GestionarAdminApartamentosUseCase gestionarAdminApartamentos;
    private final GestionarAdminActivosUseCase gestionarAdminActivos;
    private final GestionarAdminLogsUseCase gestionarAdminLogs;
    private final AdminCondominioMapper mapper;

    public AdminCondominioController(
            GestionarAdminDashboardUseCase gestionarAdminDashboard,
            GestionarAdminCondominioUseCase gestionarAdminCondominio,
            GestionarAdminEstructuraUseCase gestionarAdminEstructura,
            GestionarAdminUsuariosUseCase gestionarAdminUsuarios,
            GestionarAdminApartamentosUseCase gestionarAdminApartamentos,
            GestionarAdminActivosUseCase gestionarAdminActivos,
            GestionarAdminLogsUseCase gestionarAdminLogs,
            AdminCondominioMapper mapper) {
        this.gestionarAdminDashboard = gestionarAdminDashboard;
        this.gestionarAdminCondominio = gestionarAdminCondominio;
        this.gestionarAdminEstructura = gestionarAdminEstructura;
        this.gestionarAdminUsuarios = gestionarAdminUsuarios;
        this.gestionarAdminApartamentos = gestionarAdminApartamentos;
        this.gestionarAdminActivos = gestionarAdminActivos;
        this.gestionarAdminLogs = gestionarAdminLogs;
        this.mapper = mapper;
    }

    @GetMapping("/dashboard/metrics")
    public ResponseEntity<AdminDashboardMetricsResponse> obtenerMetricas() {
        var resultado = gestionarAdminDashboard.obtenerMetricas();
        return ResponseEntity.ok(mapper.toDashboardMetricsResponse(resultado));
    }

    @GetMapping("/condominium/my-info")
    public ResponseEntity<AdminCondominioInfoResponse> obtenerMiCondominio() {
        var resultado = gestionarAdminCondominio.obtenerMiCondominio();
        return ResponseEntity.ok(mapper.toCondominioInfoResponse(resultado));
    }

    @PutMapping("/condominium/my-info")
    public ResponseEntity<AdminCondominioInfoResponse> actualizarMiCondominio(
            @Valid @RequestBody ActualizarMiCondominioRequest request) {
        var cmd = new ActualizarMiCondominioCommand(request.nombre(), request.direccion());
        var resultado = gestionarAdminCondominio.actualizarMiCondominio(cmd);
        return ResponseEntity.ok(mapper.toCondominioInfoResponse(resultado));
    }

    @GetMapping("/condominium/configuracion")
    public ResponseEntity<AdminConfiguracionResponse> obtenerConfiguracion() {
        var resultado = gestionarAdminCondominio.obtenerConfiguracion();
        return ResponseEntity.ok(mapper.toConfiguracionResponse(resultado));
    }

    @PutMapping("/condominium/configuracion")
    public ResponseEntity<AdminConfiguracionResponse> actualizarConfiguracion(
            @Valid @RequestBody ActualizarConfiguracionRequest request) {
        var cmd = new ActualizarConfiguracionCommand(
            request.maxAutos(), request.maxMotos(), request.penalizacionPorMin(),
            request.maxTiempoPrestamoMin(), request.maxEstacionamientosPorDepto(),
            request.maxCarritosPorDepto(), request.maxVehiculosPorDepto(), request.maxInquilinosPorDepto());
        var resultado = gestionarAdminCondominio.actualizarConfiguracion(cmd);
        return ResponseEntity.ok(mapper.toConfiguracionResponse(resultado.configuracion()));
    }

    @GetMapping("/structure")
    public ResponseEntity<AdminStructureResponse> obtenerEstructura() {
        var resultado = gestionarAdminEstructura.obtenerEstructura();
        return ResponseEntity.ok(mapper.toStructureResponse(resultado));
    }

    @PostMapping("/structure/nodes")
    public ResponseEntity<Void> crearNodo(@Valid @RequestBody CrearNodeRequest request) {
        var cmd = new CrearNodeCommand(
            request.tipo(), request.nombre(), request.nombreTorre(),
            request.numero(), request.numeroPiso(), request.numeroApartamento(),
            request.metraje());
        gestionarAdminEstructura.crearNodo(cmd);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/structure/nodes/{id}")
    public ResponseEntity<Void> eliminarNodo(
            @PathVariable Long id,
            @RequestParam(defaultValue = "TORRE") String type) {
        gestionarAdminEstructura.eliminarNodo(id, type);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users")
    public ResponseEntity<PaginaResponse<AdminUserResponse>> listarUsuarios(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String rol,
            @RequestParam(required = false) Boolean activo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        var resultado = gestionarAdminUsuarios.listar(search, rol, activo, new PaginaQuery(page, size));
        return ResponseEntity.ok(mapper.toUserPaginaResponse(resultado));
    }

    @PostMapping("/users")
    public ResponseEntity<AdminUserResponse> crearUsuario(
            @Valid @RequestBody CrearAdminUserRequest request) {
        var cmd = new CrearAdminUserCommand(
            request.nombres(), request.apellidos(), request.correo(),
            request.telefono(), request.contrasena(), request.rol());
        var resultado = gestionarAdminUsuarios.crear(cmd);
        return ResponseEntity.ok(mapper.toUserResponse(resultado));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<AdminUserResponse> actualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarAdminUserRequest request) {
        var cmd = new ActualizarAdminUserCommand(
            request.nombres(), request.apellidos(), request.telefono());
        var resultado = gestionarAdminUsuarios.actualizar(id, cmd);
        return ResponseEntity.ok(mapper.toUserResponse(resultado));
    }

    @PatchMapping("/users/{id}/status")
    public ResponseEntity<Void> activarDesactivarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody EstadoActivoRequest request) {
        gestionarAdminUsuarios.activarDesactivar(id, request.activo());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/apartments")
    public ResponseEntity<PaginaResponse<AdminApartamentoDetailResponse>> listarApartamentos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        var resultado = gestionarAdminApartamentos.listar(new PaginaQuery(page, size));
        return ResponseEntity.ok(mapper.toApartamentoDetailPaginaResponse(resultado));
    }

    @PutMapping("/apartments/{id}/assign-owner")
    public ResponseEntity<Void> asignarPropietario(
            @PathVariable Long id,
            @Valid @RequestBody AsignarPropietarioRequest request) {
        gestionarAdminApartamentos.asignarPropietario(id,
            new AsignarPropietarioCommand(request.idPropietario()));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/apartments/{id}/occupants")
    public ResponseEntity<Void> actualizarOcupantes(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarOcupantesRequest request) {
        var entries = request.inquilinos().stream()
            .map(e -> new ActualizarOcupantesCommand.InquilinoEntry(
                e.nombres(), e.apellidos(),
                TipoDocumento.valueOf(e.tipoDocumento()), e.numeroDocumento()))
            .toList();
        gestionarAdminApartamentos.actualizarOcupantes(id,
            new ActualizarOcupantesCommand(entries));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/assets")
    public ResponseEntity<PaginaResponse<AdminAssetResponse>> listarActivos(
            @RequestParam String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        var resultado = gestionarAdminActivos.listar(type, new PaginaQuery(page, size));
        return ResponseEntity.ok(mapper.toAssetPaginaResponse(resultado));
    }

    @PostMapping("/assets")
    public ResponseEntity<AdminAssetResponse> crearActivo(
            @Valid @RequestBody CrearAssetRequest request) {
        var cmd = new CrearAssetCommand(request.tipo(), request.codigo(), request.numero());
        var resultado = gestionarAdminActivos.crear(cmd);
        return ResponseEntity.ok(mapper.toAssetResponse(resultado));
    }

    @PutMapping("/assets/{id}/assign-apartment")
    public ResponseEntity<AdminAssetResponse> asignarApartamento(
            @PathVariable Long id,
            @Valid @RequestBody AsignarParkingRequest request) {
        var resultado = gestionarAdminActivos.asignarApartamento(id,
            new AsignarParkingCommand(request.idApartamento()));
        return ResponseEntity.ok(mapper.toAssetResponse(resultado));
    }

    @PutMapping("/assets/{id}/status")
    public ResponseEntity<AdminAssetResponse> actualizarStatusActivo(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarStatusAssetRequest request) {
        var cmd = new ActualizarStatusAssetCommand(
            request.tipo(), request.estado(), request.disponible(),
            request.tipoVehiculo(), request.capacidadMaxima());
        var resultado = gestionarAdminActivos.actualizarStatus(id, cmd);
        return ResponseEntity.ok(mapper.toAssetResponse(resultado));
    }

    @GetMapping("/logs")
    public ResponseEntity<PaginaResponse<AdminLogEntryResponse>> listarLogs(
            @RequestParam String type,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        var inicio = fechaInicio != null ? java.time.Instant.parse(fechaInicio) : null;
        var fin = fechaFin != null ? java.time.Instant.parse(fechaFin) : null;
        var resultado = gestionarAdminLogs.listar(type, userId, inicio, fin, new PaginaQuery(page, size));
        return ResponseEntity.ok(mapper.toLogEntryPaginaResponse(resultado));
    }
}
