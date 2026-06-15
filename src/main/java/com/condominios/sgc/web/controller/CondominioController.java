package com.condominios.sgc.web.controller;

import com.condominios.sgc.application.dto.command.ActualizarCondominioCommand;
import com.condominios.sgc.application.dto.command.CrearCondominioCommand;
import com.condominios.sgc.application.dto.query.ListarCondominiosQuery;
import com.condominios.sgc.application.usecase.condominio.ActualizarCondominioPorIdUseCase;
import com.condominios.sgc.application.usecase.condominio.CrearCondominioUseCase;
import com.condominios.sgc.application.usecase.condominio.EliminarCondominioPorIdUseCase;
import com.condominios.sgc.application.usecase.condominio.ListarCondominiosUseCase;
import com.condominios.sgc.application.usecase.condominio.ObtenerCondominioPorIdUseCase;
import com.condominios.sgc.application.usecase.condominio.ObtenerDetalleCondominioUseCase;
import com.condominios.sgc.domain.auxiliar.Rol;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.infrastructure.util.JwtUtil;
import com.condominios.sgc.web.dto.request.ActualizarCondominioRequest;
import com.condominios.sgc.web.dto.request.CrearCondominioRequest;
import com.condominios.sgc.web.dto.response.CondominioResponse;
import com.condominios.sgc.web.dto.response.DetalleCondominioResponse;

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
@RequestMapping("/api/condominios")
@Validated
public class CondominioController {

    private final CrearCondominioUseCase crearCondominio;
    private final ObtenerCondominioPorIdUseCase obtenerCondominio;
    private final ListarCondominiosUseCase listarCondominios;
    private final ActualizarCondominioPorIdUseCase actualizarCondominio;
    private final EliminarCondominioPorIdUseCase eliminarCondominio;
    private final ObtenerDetalleCondominioUseCase detalleCondominio;
    private final JwtUtil jwtUtil;

    public CondominioController(CrearCondominioUseCase crearCondominio,
            ObtenerCondominioPorIdUseCase obtenerCondominio,
            ListarCondominiosUseCase listarCondominios,
            ActualizarCondominioPorIdUseCase actualizarCondominio,
            EliminarCondominioPorIdUseCase eliminarCondominio,
            ObtenerDetalleCondominioUseCase detalleCondominio,
            JwtUtil jwtUtil) {
        this.crearCondominio = crearCondominio;
        this.obtenerCondominio = obtenerCondominio;
        this.listarCondominios = listarCondominios;
        this.actualizarCondominio = actualizarCondominio;
        this.eliminarCondominio = eliminarCondominio;
        this.detalleCondominio = detalleCondominio;
        this.jwtUtil = jwtUtil;
    }

    @PreAuthorize("hasRole('SUPER_ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<CondominioResponse> crear(@RequestBody @Valid CrearCondominioRequest request) {
        var command = new CrearCondominioCommand(request.nombre(), request.idPais(), request.idCiudad(),
                request.direccion());
        var result = crearCondominio.ejecutar(command);
        return ResponseEntity.created(URI.create("/api/condominios/" + result.id()))
                .body(CondominioResponse.desdeAplicacion(result));
    }

    @PreAuthorize("hasRole('SUPER_ADMINISTRADOR')")
    @GetMapping("/{id}")
    public ResponseEntity<CondominioResponse> obtener(@PathVariable Long id) {
        var result = obtenerCondominio.ejecutar(id);
        return ResponseEntity.ok(CondominioResponse.desdeAplicacion(result));
    }

    @PreAuthorize("hasRole('SUPER_ADMINISTRADOR')")
    @GetMapping
    public ResponseEntity<PaginacionResponse<CondominioResponse>> listar(
            @RequestParam(defaultValue = "0") @Min(0) int pagina,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int tamano,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Long idPais,
            @RequestParam(required = false) Long idCiudad) {
        var query = new ListarCondominiosQuery(pagina, tamano, nombre, idPais, idCiudad);
        var result = listarCondominios.ejecutar(query);
        var contenido = result.contenido().stream()
                .map(CondominioResponse::desdeAplicacion)
                .toList();
        var webResult = new PaginacionResponse<>(contenido, result.pagina(), result.tamano(),
                result.totalElementos(), result.totalPaginas());
        return ResponseEntity.ok(webResult);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR_CONDOMINIO', 'SUPER_ADMINISTRADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<CondominioResponse> actualizar(@PathVariable Long id,
            @RequestBody @Valid ActualizarCondominioRequest request,
            @AuthenticationPrincipal Jwt jwt) {
        var usuarioJwt = jwtUtil.extraerUsuario(jwt);
        if (usuarioJwt.rol() == Rol.ADMINISTRADOR_CONDOMINIO
                && !usuarioJwt.idCondominio().equals(id)) {
            return ResponseEntity.status(403).build();
        }
        var command = new ActualizarCondominioCommand(request.nombre(), request.idPais(), request.idCiudad(),
                request.direccion());
        var result = actualizarCondominio.ejecutar(id, command);
        return ResponseEntity.ok(CondominioResponse.desdeAplicacion(result));
    }

    @PreAuthorize("hasRole('SUPER_ADMINISTRADOR')")
    @GetMapping("/{id}/detalle")
    public ResponseEntity<DetalleCondominioResponse> detalle(@PathVariable Long id) {
        var result = detalleCondominio.ejecutar(id);
        return ResponseEntity.ok(DetalleCondominioResponse.desdeAplicacion(result));
    }

    @PreAuthorize("hasRole('SUPER_ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        eliminarCondominio.ejecutar(id);
        return ResponseEntity.noContent().build();
    }
}