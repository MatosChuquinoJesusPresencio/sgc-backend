package com.condominios.sgc.infrastructure.adapter.in.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.condominios.sgc.application.dto.command.CrearPropietarioInquilinoCommand;
import com.condominios.sgc.application.dto.command.CrearPropietarioVehiculoCommand;
import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.port.in.GestionarPropietarioApartamentoUseCase;
import com.condominios.sgc.application.port.in.GestionarPropietarioDashboardUseCase;
import com.condominios.sgc.application.port.in.GestionarPropietarioInquilinosUseCase;
import com.condominios.sgc.application.port.in.GestionarPropietarioLogsUseCase;
import com.condominios.sgc.application.port.in.GestionarPropietarioVehiculosUseCase;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.AdminLogEntryResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.PaginaResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.request.CrearPropietarioInquilinoRequest;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.request.CrearPropietarioVehiculoRequest;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.PropietarioApartamentoDetailResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.PropietarioInquilinoResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.PropietarioVehiculoResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.mapper.AdminCondominioMapper;
import com.condominios.sgc.infrastructure.adapter.in.web.mapper.PropietarioMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/homeowner")
@PreAuthorize("hasRole('PROPIETARIO')")
public class PropietarioController {

    private final GestionarPropietarioDashboardUseCase dashboardUseCase;
    private final GestionarPropietarioInquilinosUseCase inquilinosUseCase;
    private final GestionarPropietarioVehiculosUseCase vehiculosUseCase;
    private final GestionarPropietarioLogsUseCase logsUseCase;
    private final GestionarPropietarioApartamentoUseCase apartamentoUseCase;
    private final PropietarioMapper mapper;
    private final AdminCondominioMapper adminMapper;

    public PropietarioController(
            GestionarPropietarioDashboardUseCase dashboardUseCase,
            GestionarPropietarioInquilinosUseCase inquilinosUseCase,
            GestionarPropietarioVehiculosUseCase vehiculosUseCase,
            GestionarPropietarioLogsUseCase logsUseCase,
            GestionarPropietarioApartamentoUseCase apartamentoUseCase,
            PropietarioMapper mapper,
            AdminCondominioMapper adminMapper) {
        this.dashboardUseCase = dashboardUseCase;
        this.inquilinosUseCase = inquilinosUseCase;
        this.vehiculosUseCase = vehiculosUseCase;
        this.logsUseCase = logsUseCase;
        this.apartamentoUseCase = apartamentoUseCase;
        this.mapper = mapper;
        this.adminMapper = adminMapper;
    }

    @GetMapping("/dashboard/summary")
    public ResponseEntity<?> obtenerResumen() {
        var resultado = dashboardUseCase.obtenerResumen();
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/apartment/details")
    public ResponseEntity<PropietarioApartamentoDetailResponse> obtenerDetalleApartamento() {
        var resultado = apartamentoUseCase.obtenerDetalle();
        return ResponseEntity.ok(mapper.toApartamentoDetailResponse(resultado));
    }

    @GetMapping("/tenants")
    public ResponseEntity<?> listarInquilinos() {
        var resultados = inquilinosUseCase.listar();
        return ResponseEntity.ok(mapper.toInquilinoResponses(resultados));
    }

    @PostMapping("/tenants")
    public ResponseEntity<PropietarioInquilinoResponse> crearInquilino(
            @Valid @RequestBody CrearPropietarioInquilinoRequest request) {
        var cmd = new CrearPropietarioInquilinoCommand(
            request.nombres(), request.apellidos(),
            request.tipoDocumento(), request.numeroDocumento());
        var resultado = inquilinosUseCase.crear(cmd);
        return ResponseEntity.ok(mapper.toInquilinoResponse(resultado));
    }

    @DeleteMapping("/tenants/{id}")
    public ResponseEntity<Void> eliminarInquilino(@PathVariable Long id) {
        inquilinosUseCase.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/vehicles")
    public ResponseEntity<?> listarVehiculos() {
        var resultados = vehiculosUseCase.listar();
        return ResponseEntity.ok(mapper.toVehiculoResponses(resultados));
    }

    @PostMapping("/vehicles")
    public ResponseEntity<PropietarioVehiculoResponse> crearVehiculo(
            @Valid @RequestBody CrearPropietarioVehiculoRequest request) {
        var cmd = new CrearPropietarioVehiculoCommand(
            request.marca(), request.color(), request.modelo(),
            request.placa(), request.tipo());
        var resultado = vehiculosUseCase.crear(cmd);
        return ResponseEntity.ok(mapper.toVehiculoResponse(resultado));
    }

    @DeleteMapping("/vehicles/{id}")
    public ResponseEntity<Void> eliminarVehiculo(@PathVariable Long id) {
        vehiculosUseCase.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/logs")
    public ResponseEntity<PaginaResponse<AdminLogEntryResponse>> listarLogs(
            @RequestParam String type,
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        var inicio = fechaInicio != null ? java.time.Instant.parse(fechaInicio) : null;
        var fin = fechaFin != null ? java.time.Instant.parse(fechaFin) : null;
        var resultado = logsUseCase.listar(type, inicio, fin, new PaginaQuery(page, size));
        return ResponseEntity.ok(adminMapper.toLogEntryPaginaResponse(resultado));
    }
}
