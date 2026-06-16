package com.condominios.sgc.web.controller;

import com.condominios.sgc.application.dto.command.CrearTorreCommand;
import com.condominios.sgc.application.dto.query.ListarTorresQuery;
import com.condominios.sgc.application.usecase.torre.ActualizarTorrePorIdUseCase;
import com.condominios.sgc.application.usecase.torre.CrearTorreUseCase;
import com.condominios.sgc.application.usecase.torre.EliminarTorrePorIdUseCase;
import com.condominios.sgc.application.usecase.torre.ListarTorresUseCase;
import com.condominios.sgc.application.usecase.torre.ObtenerTorrePorIdUseCase;
import com.condominios.sgc.domain.auxiliar.Rol;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.infrastructure.util.JwtUtil;
import com.condominios.sgc.web.dto.request.ActualizarTorreRequest;
import com.condominios.sgc.web.dto.request.CrearTorreRequest;
import com.condominios.sgc.web.dto.response.TorreResponse;
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
@RequestMapping("/api/torres")
@Validated
public class TorreController {

    private final CrearTorreUseCase crearTorre;
    private final ObtenerTorrePorIdUseCase obtenerTorre;
    private final ListarTorresUseCase listarTorres;
    private final ActualizarTorrePorIdUseCase actualizarTorre;
    private final EliminarTorrePorIdUseCase eliminarTorre;
    private final JwtUtil jwtUtil;

    public TorreController(CrearTorreUseCase crearTorre,
            ObtenerTorrePorIdUseCase obtenerTorre,
            ListarTorresUseCase listarTorres,
            ActualizarTorrePorIdUseCase actualizarTorre,
            EliminarTorrePorIdUseCase eliminarTorre,
            JwtUtil jwtUtil) {
        this.crearTorre = crearTorre;
        this.obtenerTorre = obtenerTorre;
        this.listarTorres = listarTorres;
        this.actualizarTorre = actualizarTorre;
        this.eliminarTorre = eliminarTorre;
        this.jwtUtil = jwtUtil;
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @PostMapping
    public ResponseEntity<TorreResponse> crear(@RequestBody @Valid CrearTorreRequest request,
            @AuthenticationPrincipal Jwt jwt) {
        var usuarioJwt = jwtUtil.extraerUsuario(jwt);
        var idCondominio = usuarioJwt.rol() == Rol.ADMINISTRADOR_CONDOMINIO
                ? usuarioJwt.idCondominio() : request.idCondominio();
        var command = new CrearTorreCommand(request.nombre(), idCondominio);
        var result = crearTorre.ejecutar(command);
        return ResponseEntity.created(URI.create("/api/torres/" + result.id()))
                .body(TorreResponse.desdeAplicacion(result));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @GetMapping("/{id}")
    public ResponseEntity<TorreResponse> obtener(@PathVariable Long id) {
        var result = obtenerTorre.ejecutar(id);
        return ResponseEntity.ok(TorreResponse.desdeAplicacion(result));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @GetMapping
    public ResponseEntity<PaginacionResponse<TorreResponse>> listar(
            @RequestParam(defaultValue = "0") @Min(0) int pagina,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int tamano,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Long idCondominio,
            @AuthenticationPrincipal Jwt jwt) {
        var usuarioJwt = jwtUtil.extraerUsuario(jwt);
        if (usuarioJwt.rol() == Rol.ADMINISTRADOR_CONDOMINIO) {
            idCondominio = usuarioJwt.idCondominio();
        }
        var query = new ListarTorresQuery(pagina, tamano, nombre, idCondominio);
        var result = listarTorres.ejecutar(query);
        var contenido = result.contenido().stream()
                .map(TorreResponse::desdeAplicacion)
                .toList();
        var webResult = new PaginacionResponse<>(contenido, result.pagina(), result.tamano(),
                result.totalElementos(), result.totalPaginas());
        return ResponseEntity.ok(webResult);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @PutMapping("/{id}")
    public ResponseEntity<TorreResponse> actualizar(@PathVariable Long id,
            @RequestBody @Valid ActualizarTorreRequest request) {
        var result = actualizarTorre.ejecutar(id, request.nombre());
        return ResponseEntity.ok(TorreResponse.desdeAplicacion(result));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        eliminarTorre.ejecutar(id);
        return ResponseEntity.noContent().build();
    }
}
