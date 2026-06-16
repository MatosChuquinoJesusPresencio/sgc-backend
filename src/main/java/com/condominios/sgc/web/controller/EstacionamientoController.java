package com.condominios.sgc.web.controller;

import com.condominios.sgc.application.dto.command.ActualizarEstacionamientoCommand;
import com.condominios.sgc.application.dto.command.CrearEstacionamientoCommand;
import com.condominios.sgc.application.dto.query.ListarEstacionamientosQuery;
import com.condominios.sgc.application.usecase.estacionamiento.ActualizarEstacionamientoPorIdUseCase;
import com.condominios.sgc.application.usecase.estacionamiento.CrearEstacionamientoUseCase;
import com.condominios.sgc.application.usecase.estacionamiento.EliminarEstacionamientoPorIdUseCase;
import com.condominios.sgc.application.usecase.estacionamiento.ListarEstacionamientosUseCase;
import com.condominios.sgc.application.usecase.estacionamiento.ObtenerEstacionamientoPorIdUseCase;
import com.condominios.sgc.domain.auxiliar.Rol;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.infrastructure.util.JwtUtil;
import com.condominios.sgc.web.dto.request.ActualizarEstacionamientoRequest;
import com.condominios.sgc.web.dto.request.CrearEstacionamientoRequest;
import com.condominios.sgc.web.dto.response.EstacionamientoResponse;
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
@RequestMapping("/api/estacionamientos")
@Validated
public class EstacionamientoController {

    private final CrearEstacionamientoUseCase crearEstacionamiento;
    private final ObtenerEstacionamientoPorIdUseCase obtenerEstacionamiento;
    private final ListarEstacionamientosUseCase listarEstacionamientos;
    private final ActualizarEstacionamientoPorIdUseCase actualizarEstacionamiento;
    private final EliminarEstacionamientoPorIdUseCase eliminarEstacionamiento;
    private final JwtUtil jwtUtil;

    public EstacionamientoController(CrearEstacionamientoUseCase crearEstacionamiento,
            ObtenerEstacionamientoPorIdUseCase obtenerEstacionamiento,
            ListarEstacionamientosUseCase listarEstacionamientos,
            ActualizarEstacionamientoPorIdUseCase actualizarEstacionamiento,
            EliminarEstacionamientoPorIdUseCase eliminarEstacionamiento,
            JwtUtil jwtUtil) {
        this.crearEstacionamiento = crearEstacionamiento;
        this.obtenerEstacionamiento = obtenerEstacionamiento;
        this.listarEstacionamientos = listarEstacionamientos;
        this.actualizarEstacionamiento = actualizarEstacionamiento;
        this.eliminarEstacionamiento = eliminarEstacionamiento;
        this.jwtUtil = jwtUtil;
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @PostMapping
    public ResponseEntity<EstacionamientoResponse> crear(@RequestBody @Valid CrearEstacionamientoRequest request,
            @AuthenticationPrincipal Jwt jwt) {
        var usuarioJwt = jwtUtil.extraerUsuario(jwt);
        var idCondominio = usuarioJwt.rol() == Rol.ADMINISTRADOR_CONDOMINIO
                ? usuarioJwt.idCondominio() : request.idCondominio();
        var command = new CrearEstacionamientoCommand(request.numero(), idCondominio);
        var result = crearEstacionamiento.ejecutar(command);
        return ResponseEntity.created(URI.create("/api/estacionamientos/" + result.id()))
                .body(EstacionamientoResponse.desdeAplicacion(result));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @GetMapping("/{id}")
    public ResponseEntity<EstacionamientoResponse> obtener(@PathVariable Long id) {
        var result = obtenerEstacionamiento.ejecutar(id);
        return ResponseEntity.ok(EstacionamientoResponse.desdeAplicacion(result));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @GetMapping
    public ResponseEntity<PaginacionResponse<EstacionamientoResponse>> listar(
            @RequestParam(defaultValue = "0") @Min(0) int pagina,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int tamano,
            @RequestParam(required = false) Integer numero,
            @RequestParam(required = false) String tipoVehiculo,
            @RequestParam(required = false) Boolean disponible,
            @RequestParam(required = false) Long idApartamento,
            @RequestParam(required = false) Long idCondominio,
            @AuthenticationPrincipal Jwt jwt) {
        var usuarioJwt = jwtUtil.extraerUsuario(jwt);
        if (usuarioJwt.rol() == Rol.ADMINISTRADOR_CONDOMINIO) {
            idCondominio = usuarioJwt.idCondominio();
        }
        var query = new ListarEstacionamientosQuery(pagina, tamano, numero, null,
                disponible, idApartamento, idCondominio);
        var result = listarEstacionamientos.ejecutar(query);
        var contenido = result.contenido().stream()
                .map(EstacionamientoResponse::desdeAplicacion)
                .toList();
        var webResult = new PaginacionResponse<>(contenido, result.pagina(), result.tamano(),
                result.totalElementos(), result.totalPaginas());
        return ResponseEntity.ok(webResult);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @PutMapping("/{id}")
    public ResponseEntity<EstacionamientoResponse> actualizar(@PathVariable Long id,
            @RequestBody @Valid ActualizarEstacionamientoRequest request) {
        var command = new ActualizarEstacionamientoCommand(request.numero(), request.tipoVehiculo(),
                request.capacidadMaxima(), request.idApartamento(), request.desasignarApartamento());
        var result = actualizarEstacionamiento.ejecutar(id, command);
        return ResponseEntity.ok(EstacionamientoResponse.desdeAplicacion(result));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        eliminarEstacionamiento.ejecutar(id);
        return ResponseEntity.noContent().build();
    }
}
