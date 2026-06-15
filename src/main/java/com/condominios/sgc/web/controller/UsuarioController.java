package com.condominios.sgc.web.controller;

import com.condominios.sgc.application.dto.command.ActualizarUsuarioCommand;
import com.condominios.sgc.application.dto.command.CrearUsuarioCommand;
import com.condominios.sgc.application.dto.command.SolicitarCambioCorreoCommand;
import com.condominios.sgc.application.dto.query.ListarUsuariosQuery;
import com.condominios.sgc.application.usecase.usuario.ActualizarUsuarioPorIdUseCase;
import com.condominios.sgc.application.usecase.usuario.CambiarEstadoActivoUsuarioUseCase;
import com.condominios.sgc.application.usecase.usuario.CrearUsuarioUseCase;
import com.condominios.sgc.application.usecase.usuario.EliminarUsuarioPorIdUseCase;
import com.condominios.sgc.application.usecase.usuario.ListarUsuariosUseCase;
import com.condominios.sgc.application.usecase.usuario.ObtenerUsuarioPorIdUseCase;
import com.condominios.sgc.application.usecase.usuario.SolicitarCambioCorreoUseCase;
import com.condominios.sgc.domain.auxiliar.Rol;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.infrastructure.util.JwtUtil;
import com.condominios.sgc.web.dto.request.ActualizarUsuarioRequest;
import com.condominios.sgc.web.dto.request.CambiarEstadoActivoRequest;
import com.condominios.sgc.web.dto.request.CrearUsuarioRequest;
import com.condominios.sgc.web.dto.request.SolicitarCambioCorreoRequest;
import com.condominios.sgc.web.dto.response.UsuarioResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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
import java.net.URI;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final CrearUsuarioUseCase crearUsuario;
    private final ObtenerUsuarioPorIdUseCase obtenerUsuario;
    private final ListarUsuariosUseCase listarUsuarios;
    private final ActualizarUsuarioPorIdUseCase actualizarUsuario;
    private final EliminarUsuarioPorIdUseCase eliminarUsuario;
    private final CambiarEstadoActivoUsuarioUseCase cambiarEstadoActivo;
    private final SolicitarCambioCorreoUseCase solicitarCambioCorreo;
    private final JwtUtil jwtUtil;

    public UsuarioController(CrearUsuarioUseCase crearUsuario,
            ObtenerUsuarioPorIdUseCase obtenerUsuario,
            ListarUsuariosUseCase listarUsuarios,
            ActualizarUsuarioPorIdUseCase actualizarUsuario,
            EliminarUsuarioPorIdUseCase eliminarUsuario,
            CambiarEstadoActivoUsuarioUseCase cambiarEstadoActivo,
            SolicitarCambioCorreoUseCase solicitarCambioCorreo,
            JwtUtil jwtUtil) {
        this.crearUsuario = crearUsuario;
        this.obtenerUsuario = obtenerUsuario;
        this.listarUsuarios = listarUsuarios;
        this.actualizarUsuario = actualizarUsuario;
        this.eliminarUsuario = eliminarUsuario;
        this.cambiarEstadoActivo = cambiarEstadoActivo;
        this.solicitarCambioCorreo = solicitarCambioCorreo;
        this.jwtUtil = jwtUtil;
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @PostMapping
    public ResponseEntity<UsuarioResponse> crear(@RequestBody @Valid CrearUsuarioRequest request,
            @AuthenticationPrincipal Jwt jwt) {
        var rolSolicitante = jwtUtil.extraerUsuario(jwt).rol();
        var command = new CrearUsuarioCommand(request.nombres(), request.apellidos(),
                request.correo(), request.telefono(), request.rol(), request.idCondominio(),
                rolSolicitante);
        var result = crearUsuario.ejecutar(command);
        return ResponseEntity.created(URI.create("/api/usuarios/" + result.id()))
                .body(UsuarioResponse.desdeAplicacion(result));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> obtener(@PathVariable Long id) {
        var result = obtenerUsuario.ejecutar(id);
        return ResponseEntity.ok(UsuarioResponse.desdeAplicacion(result));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @GetMapping
    public ResponseEntity<PaginacionResponse<UsuarioResponse>> listar(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamano,
            @RequestParam(required = false) String nombres,
            @RequestParam(required = false) String apellidos,
            @RequestParam(required = false) String correo,
            @RequestParam(required = false) Rol rol,
            @RequestParam(required = false) Boolean activo,
            @RequestParam(required = false) Long idCondominio) {
        var query = new ListarUsuariosQuery(pagina, tamano, nombres, apellidos,
                correo, rol, activo, idCondominio);
        var result = listarUsuarios.ejecutar(query);
        var contenido = result.contenido().stream()
                .map(UsuarioResponse::desdeAplicacion)
                .toList();
        var webResult = new PaginacionResponse<>(contenido, result.pagina(), result.tamano(),
                result.totalElementos(), result.totalPaginas());
        return ResponseEntity.ok(webResult);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> actualizar(@PathVariable Long id,
            @RequestBody @Valid ActualizarUsuarioRequest request,
            @AuthenticationPrincipal Jwt jwt) {
        var rolSolicitante = jwtUtil.extraerUsuario(jwt).rol();
        var command = new ActualizarUsuarioCommand(request.nombres(), request.apellidos(),
                request.telefono(), request.rol(), request.idCondominio(),
                request.desasignarCondominio(), rolSolicitante);
        var result = actualizarUsuario.ejecutar(id, command);
        return ResponseEntity.ok(UsuarioResponse.desdeAplicacion(result));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        eliminarUsuario.ejecutar(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @PatchMapping("/{id}/estado-activo")
    public ResponseEntity<UsuarioResponse> cambiarEstadoActivo(@PathVariable Long id,
            @RequestBody @Valid CambiarEstadoActivoRequest request) {
        var result = cambiarEstadoActivo.ejecutar(id, request.activo());
        return ResponseEntity.ok(UsuarioResponse.desdeAplicacion(result));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{id}/solicitar-cambio-correo")
    public ResponseEntity<Void> solicitarCambioCorreo(@PathVariable Long id,
            @RequestBody @Valid SolicitarCambioCorreoRequest request) {
        var command = new SolicitarCambioCorreoCommand(id, request.nuevoCorreo());
        solicitarCambioCorreo.ejecutar(command);
        return ResponseEntity.ok().build();
    }
}
