package com.condominios.sgc.infrastructure.adapter.in.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.condominios.sgc.application.dto.command.RegistrarEntradaVehiculoCommand;
import com.condominios.sgc.application.dto.command.RegistrarPrestamoCarritoCommand;
import com.condominios.sgc.application.dto.command.RegistrarSalidaVehiculoCommand;
import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.port.in.GestionarSeguridadAccesoUseCase;
import com.condominios.sgc.application.port.in.GestionarSeguridadDashboardUseCase;
import com.condominios.sgc.application.port.in.GestionarSeguridadEstacionamientosUseCase;
import com.condominios.sgc.application.port.in.GestionarSeguridadPrestamosUseCase;
import com.condominios.sgc.application.port.in.GestionarSeguridadVehiculosUseCase;
import com.condominios.sgc.domain.type.MetodoEntrada;
import com.condominios.sgc.domain.type.TipoHabitante;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.request.ActualizarEstadoCarritoRequest;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.request.RegistrarEntradaVehiculoRequest;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.request.RegistrarPrestamoCarritoRequest;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.request.RegistrarSalidaVehiculoRequest;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.AdminLogEntryResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.SecurityActiveCartLoanResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.SecurityApartamentoResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.SecurityCartAssetResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.SecurityCartLoanFullResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.SecurityDashboardResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.SecurityParkingSlotResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.SecurityUnassignedVehicleResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.SecurityVehicleVerificationResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.mapper.AdminCondominioMapper;
import com.condominios.sgc.infrastructure.adapter.in.web.mapper.SeguridadMapper;
import java.util.List;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/security")
@PreAuthorize("hasAnyRole('AGENTE_SEGURIDAD', 'SUPER_ADMINISTRADOR')")
public class SecurityController {

    private final GestionarSeguridadDashboardUseCase dashboardUseCase;
    private final GestionarSeguridadEstacionamientosUseCase estacionamientosUseCase;
    private final GestionarSeguridadVehiculosUseCase vehiculosUseCase;
    private final GestionarSeguridadPrestamosUseCase prestamosUseCase;
    private final GestionarSeguridadAccesoUseCase accesoUseCase;
    private final SeguridadMapper mapper;
    private final AdminCondominioMapper adminMapper;

    public SecurityController(
            GestionarSeguridadDashboardUseCase dashboardUseCase,
            GestionarSeguridadEstacionamientosUseCase estacionamientosUseCase,
            GestionarSeguridadVehiculosUseCase vehiculosUseCase,
            GestionarSeguridadPrestamosUseCase prestamosUseCase,
            GestionarSeguridadAccesoUseCase accesoUseCase,
            SeguridadMapper mapper,
            AdminCondominioMapper adminMapper) {
        this.dashboardUseCase = dashboardUseCase;
        this.estacionamientosUseCase = estacionamientosUseCase;
        this.vehiculosUseCase = vehiculosUseCase;
        this.prestamosUseCase = prestamosUseCase;
        this.accesoUseCase = accesoUseCase;
        this.mapper = mapper;
        this.adminMapper = adminMapper;
    }

    @GetMapping("/dashboard/status")
    public ResponseEntity<SecurityDashboardResponse> obtenerStatus(
            @RequestParam(required = false) Long condominioId) {
        var resultado = dashboardUseCase.obtenerStatus(condominioId);
        return ResponseEntity.ok(mapper.toDashboardResponse(resultado));
    }

    @GetMapping("/parking-slots")
    public ResponseEntity<List<SecurityParkingSlotResponse>> listarSlots(
            @RequestParam(required = false) Long condominioId) {
        var resultados = estacionamientosUseCase.listarSlots(condominioId);
        return ResponseEntity.ok(mapper.toParkingSlotResponses(resultados));
    }

    @GetMapping("/vehicles/verify/{plate}")
    public ResponseEntity<SecurityVehicleVerificationResponse> verificarVehiculo(@PathVariable String plate) {
        var resultado = vehiculosUseCase.verificarPorPlaca(plate);
        return ResponseEntity.ok(mapper.toVehicleVerificationResponse(resultado));
    }

    @GetMapping("/vehicles/unassigned")
    public ResponseEntity<List<SecurityUnassignedVehicleResponse>> listarSinEstacionamiento(
            @RequestParam(required = false) Long condominioId) {
        var resultados = vehiculosUseCase.listarSinEstacionamiento(condominioId);
        return ResponseEntity.ok(mapper.toUnassignedVehicleResponses(resultados));
    }

    @GetMapping("/asset-loans/active-carts")
    public ResponseEntity<List<SecurityActiveCartLoanResponse>> listarPrestamosActivos(
            @RequestParam(required = false) Long condominioId) {
        var resultados = prestamosUseCase.listarActivos(condominioId);
        return ResponseEntity.ok(mapper.toActiveCartLoanResponses(resultados));
    }

    @GetMapping("/asset-loans/all")
    public ResponseEntity<List<SecurityCartLoanFullResponse>> listarTodosPrestamos(
            @RequestParam(required = false) Long condominioId) {
        var resultados = prestamosUseCase.listarTodos(condominioId);
        return ResponseEntity.ok(mapper.toCartLoanFullResponses(resultados));
    }

    @PostMapping("/asset-loans")
    public ResponseEntity<SecurityActiveCartLoanResponse> registrarPrestamo(
            @RequestParam(required = false) Long condominioId,
            @Valid @RequestBody RegistrarPrestamoCarritoRequest request) {
        var cmd = new RegistrarPrestamoCarritoCommand(
            request.codigoCarrito(), request.numeroApartamento(),
            request.nombreSolicitante(), request.dniSolicitante(),
            request.solicitante(), request.idPropietario(), request.idInquilino());
        var resultado = prestamosUseCase.registrarPrestamo(condominioId, cmd);
        return ResponseEntity.ok(mapper.toActiveCartLoanResponse(resultado));
    }

    @PutMapping("/asset-loans/{id}/return")
    public ResponseEntity<Void> registrarDevolucion(@PathVariable Long id) {
        prestamosUseCase.registrarDevolucion(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/access-logs")
    public ResponseEntity<?> listarAccessLogs(
            @RequestParam(required = false) Long condominioId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        var resultado = accesoUseCase.listarLogs(condominioId, new PaginaQuery(page, size));
        return ResponseEntity.ok(adminMapper.toLogEntryPaginaResponse(resultado));
    }

    @GetMapping("/carts")
    public ResponseEntity<List<SecurityCartAssetResponse>> listarCarritos(
            @RequestParam(required = false) Long condominioId) {
        var resultados = prestamosUseCase.listarCarritos(condominioId);
        return ResponseEntity.ok(mapper.toCartAssetResponses(resultados));
    }

    @GetMapping("/apartments")
    public ResponseEntity<List<SecurityApartamentoResponse>> listarApartamentos(
            @RequestParam(required = false) Long condominioId) {
        var resultados = prestamosUseCase.listarApartamentos(condominioId);
        return ResponseEntity.ok(mapper.toApartamentoResponses(resultados));
    }

    @PutMapping("/carts/{id}/state")
    public ResponseEntity<Void> actualizarEstadoCarrito(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarEstadoCarritoRequest request) {
        prestamosUseCase.actualizarEstadoCarrito(id, request.estado());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/access-logs/entry")
    public ResponseEntity<AdminLogEntryResponse> registrarEntrada(
            @RequestParam(required = false) Long condominioId,
            @Valid @RequestBody RegistrarEntradaVehiculoRequest request) {
        var cmd = new RegistrarEntradaVehiculoCommand(
            request.placa(),
            MetodoEntrada.valueOf(request.metodo()),
            request.ocupante() != null ? TipoHabitante.valueOf(request.ocupante()) : null,
            request.datosInquilino(),
            request.idEstacionamiento());
        var resultado = accesoUseCase.registrarEntrada(condominioId, cmd);
        return ResponseEntity.ok(adminMapper.toLogEntryResponse(resultado));
    }

    @PutMapping("/access-logs/exit")
    public ResponseEntity<AdminLogEntryResponse> registrarSalida(
            @Valid @RequestBody RegistrarSalidaVehiculoRequest request) {
        var cmd = new RegistrarSalidaVehiculoCommand(request.idLogAcceso());
        var resultado = accesoUseCase.registrarSalida(cmd);
        return ResponseEntity.ok(adminMapper.toLogEntryResponse(resultado));
    }
}
