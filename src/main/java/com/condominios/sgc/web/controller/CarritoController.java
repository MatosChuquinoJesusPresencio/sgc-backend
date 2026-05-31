package com.condominios.sgc.web.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.http.HttpStatus;
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

import com.condominios.sgc.application.dto.ActualizarCarritoRequest;
import com.condominios.sgc.application.dto.CrearCarritoRequest;
import com.condominios.sgc.application.dto.IniciarPrestamoRequest;
import com.condominios.sgc.application.service.CarritoService;
import com.condominios.sgc.application.service.LogPrestamoCarritoService;
import com.condominios.sgc.domain.auxiliar.EstadoCarrito;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.web.dto.CarritoResponse;
import com.condominios.sgc.web.dto.LogPrestamoCarritoResponse;

@RestController
@RequestMapping("/api/carritos")
public class CarritoController {

    private final CarritoService carritoService;
    private final LogPrestamoCarritoService logPrestamoService;

    public CarritoController(CarritoService carritoService, LogPrestamoCarritoService logPrestamoService) {
        this.carritoService = carritoService;
        this.logPrestamoService = logPrestamoService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<CarritoResponse> crear(@RequestBody CrearCarritoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(CarritoResponse.fromModel(carritoService.crear(request)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<PaginacionResponse<CarritoResponse>> listar(
            @RequestParam Map<String, String> params) {
        var req = PaginacionRequest.desdeParams(params);
        var filtros = req.filtros();
        if (filtros == null || !filtros.containsKey("condominioId")) return ResponseEntity.badRequest().build();
        Long condominioId = Long.valueOf(filtros.get("condominioId"));
        PaginacionResponse<CarritoResponse> content = carritoService.listarPorCondominio(condominioId, req)
                .map(CarritoResponse::fromModel);
        return ResponseEntity.ok(content);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CarritoResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(CarritoResponse.fromModel(carritoService.obtener(id)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<CarritoResponse> actualizar(
            @PathVariable Long id,
            @RequestBody ActualizarCarritoRequest request) {
        return ResponseEntity.ok(CarritoResponse.fromModel(carritoService.actualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        carritoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/estado")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<CarritoResponse> cambiarEstado(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        var estadoStr = body.get("estado");
        if (estadoStr == null) return ResponseEntity.badRequest().build();
        var nuevoEstado = EstadoCarrito.valueOf(estadoStr);
        return ResponseEntity.ok(CarritoResponse.fromModel(carritoService.cambiarEstado(id, nuevoEstado)));
    }

    @PostMapping("/{carritoId}/prestar")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LogPrestamoCarritoResponse> iniciarPrestamo(
            @PathVariable Long carritoId,
            @RequestBody IniciarPrestamoRequest request) {
        var reqCompleto = new IniciarPrestamoRequest(
            request.solicitante(), request.nombreSolicitante(),
            request.dniSolicitante(), request.apartamentoId(), carritoId);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(LogPrestamoCarritoResponse.fromModel(logPrestamoService.iniciar(reqCompleto)));
    }

    @PostMapping("/prestamos/{logId}/devolver")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<LogPrestamoCarritoResponse> finalizarPrestamo(
            @PathVariable Long logId,
            @RequestBody Map<String, BigDecimal> body) {
        return ResponseEntity.ok(LogPrestamoCarritoResponse.fromModel(
            logPrestamoService.finalizar(logId, body.get("penalizacion"))));
    }
}
