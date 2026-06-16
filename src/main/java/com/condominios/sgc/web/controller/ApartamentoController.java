package com.condominios.sgc.web.controller;

import com.condominios.sgc.application.dto.command.ActualizarApartamentoCommand;
import com.condominios.sgc.application.dto.command.CrearApartamentoCommand;
import com.condominios.sgc.application.dto.query.ListarApartamentosQuery;
import com.condominios.sgc.application.usecase.apartamento.ActualizarApartamentoPorIdUseCase;
import com.condominios.sgc.application.usecase.apartamento.CrearApartamentoUseCase;
import com.condominios.sgc.application.usecase.apartamento.EliminarApartamentoPorIdUseCase;
import com.condominios.sgc.application.usecase.apartamento.ListarApartamentosUseCase;
import com.condominios.sgc.application.usecase.apartamento.ObtenerApartamentoPorIdUseCase;
import com.condominios.sgc.application.usecase.apartamento.ObtenerApartamentoPorPropietarioUseCase;
import com.condominios.sgc.application.usecase.apartamento.ObtenerDetalleApartamentoUseCase;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.infrastructure.util.JwtUtil;
import com.condominios.sgc.web.dto.request.ActualizarApartamentoRequest;
import com.condominios.sgc.web.dto.request.CrearApartamentoRequest;
import com.condominios.sgc.web.dto.response.ApartamentoResponse;
import com.condominios.sgc.web.dto.response.DetalleApartamentoResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;

@RestController
@RequestMapping("/api/apartamentos")
@Validated
public class ApartamentoController {

    private final CrearApartamentoUseCase crearApartamento;
    private final ObtenerApartamentoPorIdUseCase obtenerApartamento;
    private final ListarApartamentosUseCase listarApartamentos;
    private final ActualizarApartamentoPorIdUseCase actualizarApartamento;
    private final EliminarApartamentoPorIdUseCase eliminarApartamento;
    private final ObtenerApartamentoPorPropietarioUseCase obtenerApartamentoPorPropietario;
    private final ObtenerDetalleApartamentoUseCase obtenerDetalleApartamento;
    private final JwtUtil jwtUtil;

    public ApartamentoController(CrearApartamentoUseCase crearApartamento,
            ObtenerApartamentoPorIdUseCase obtenerApartamento,
            ListarApartamentosUseCase listarApartamentos,
            ActualizarApartamentoPorIdUseCase actualizarApartamento,
            EliminarApartamentoPorIdUseCase eliminarApartamento,
            ObtenerApartamentoPorPropietarioUseCase obtenerApartamentoPorPropietario,
            ObtenerDetalleApartamentoUseCase obtenerDetalleApartamento,
            JwtUtil jwtUtil) {
        this.crearApartamento = crearApartamento;
        this.obtenerApartamento = obtenerApartamento;
        this.listarApartamentos = listarApartamentos;
        this.actualizarApartamento = actualizarApartamento;
        this.eliminarApartamento = eliminarApartamento;
        this.obtenerApartamentoPorPropietario = obtenerApartamentoPorPropietario;
        this.obtenerDetalleApartamento = obtenerDetalleApartamento;
        this.jwtUtil = jwtUtil;
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @PostMapping
    public ResponseEntity<ApartamentoResponse> crear(@RequestBody @Valid CrearApartamentoRequest request) {
        var command = new CrearApartamentoCommand(request.numero(), request.metraje(), request.idPiso());
        var result = crearApartamento.ejecutar(command);
        return ResponseEntity.created(URI.create("/api/apartamentos/" + result.id()))
                .body(ApartamentoResponse.desdeAplicacion(result));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @GetMapping("/{id}")
    public ResponseEntity<ApartamentoResponse> obtener(@PathVariable Long id) {
        var result = obtenerApartamento.ejecutar(id);
        return ResponseEntity.ok(ApartamentoResponse.desdeAplicacion(result));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @GetMapping
    public ResponseEntity<PaginacionResponse<ApartamentoResponse>> listar(
            @RequestParam(defaultValue = "0") @Min(0) int pagina,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int tamano,
            @RequestParam(required = false) Integer numero,
            @RequestParam(required = false) Long idPiso,
            @RequestParam(required = false) Long idPropietario,
            @RequestParam(required = false) Boolean derechoEstacionamiento) {
        var query = new ListarApartamentosQuery(pagina, tamano, numero, idPiso,
                idPropietario, derechoEstacionamiento);
        var result = listarApartamentos.ejecutar(query);
        var contenido = result.contenido().stream()
                .map(ApartamentoResponse::desdeAplicacion)
                .toList();
        var webResult = new PaginacionResponse<>(contenido, result.pagina(), result.tamano(),
                result.totalElementos(), result.totalPaginas());
        return ResponseEntity.ok(webResult);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @PutMapping("/{id}")
    public ResponseEntity<ApartamentoResponse> actualizar(@PathVariable Long id,
            @RequestBody @Valid ActualizarApartamentoRequest request) {
        var command = new ActualizarApartamentoCommand(request.numero(), request.metraje(),
                request.desasignarPropietario(), request.idPropietario());
        var result = actualizarApartamento.ejecutar(id, command);
        return ResponseEntity.ok(ApartamentoResponse.desdeAplicacion(result));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        eliminarApartamento.ejecutar(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mi-apartamento")
    public ResponseEntity<ApartamentoResponse> miApartamento(@AuthenticationPrincipal Jwt jwt) {
        var usuarioJwt = jwtUtil.extraerUsuario(jwt);
        var result = obtenerApartamentoPorPropietario.ejecutar(usuarioJwt.idUsuario());
        return ResponseEntity.ok(ApartamentoResponse.desdeAplicacion(result));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @GetMapping("/{id}/detalle")
    public ResponseEntity<DetalleApartamentoResponse> detalle(@PathVariable Long id) {
        var result = obtenerDetalleApartamento.ejecutar(id);
        return ResponseEntity.ok(DetalleApartamentoResponse.desdeAplicacion(result));
    }
}
