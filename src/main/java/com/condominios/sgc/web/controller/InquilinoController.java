package com.condominios.sgc.web.controller;

import com.condominios.sgc.application.dto.command.ActualizarInquilinoCommand;
import com.condominios.sgc.application.dto.command.CrearInquilinoCommand;
import com.condominios.sgc.application.dto.query.ListarInquilinosQuery;
import com.condominios.sgc.application.usecase.inquilino.ActualizarInquilinoPorIdUseCase;
import com.condominios.sgc.application.usecase.inquilino.CrearInquilinoUseCase;
import com.condominios.sgc.application.usecase.inquilino.EliminarInquilinoPorIdUseCase;
import com.condominios.sgc.application.usecase.inquilino.ListarInquilinosUseCase;
import com.condominios.sgc.application.usecase.inquilino.ObtenerInquilinoPorDocumentoUseCase;
import com.condominios.sgc.application.usecase.inquilino.ObtenerInquilinoPorIdUseCase;
import com.condominios.sgc.domain.auxiliar.Rol;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.infrastructure.util.JwtUtil;
import com.condominios.sgc.web.dto.request.ActualizarInquilinoRequest;
import com.condominios.sgc.web.dto.request.CrearInquilinoRequest;
import com.condominios.sgc.web.dto.response.InquilinoResponse;
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
@RequestMapping("/api/inquilinos")
@Validated
public class InquilinoController {

    private final CrearInquilinoUseCase crearInquilino;
    private final ObtenerInquilinoPorIdUseCase obtenerInquilino;
    private final ObtenerInquilinoPorDocumentoUseCase obtenerInquilinoPorDocumento;
    private final ListarInquilinosUseCase listarInquilinos;
    private final ActualizarInquilinoPorIdUseCase actualizarInquilino;
    private final EliminarInquilinoPorIdUseCase eliminarInquilino;
    private final JwtUtil jwtUtil;

    public InquilinoController(CrearInquilinoUseCase crearInquilino,
            ObtenerInquilinoPorIdUseCase obtenerInquilino,
            ObtenerInquilinoPorDocumentoUseCase obtenerInquilinoPorDocumento,
            ListarInquilinosUseCase listarInquilinos,
            ActualizarInquilinoPorIdUseCase actualizarInquilino,
            EliminarInquilinoPorIdUseCase eliminarInquilino,
            JwtUtil jwtUtil) {
        this.crearInquilino = crearInquilino;
        this.obtenerInquilino = obtenerInquilino;
        this.obtenerInquilinoPorDocumento = obtenerInquilinoPorDocumento;
        this.listarInquilinos = listarInquilinos;
        this.actualizarInquilino = actualizarInquilino;
        this.eliminarInquilino = eliminarInquilino;
        this.jwtUtil = jwtUtil;
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @PostMapping
    public ResponseEntity<InquilinoResponse> crear(@RequestBody @Valid CrearInquilinoRequest request,
            @AuthenticationPrincipal Jwt jwt) {
        var usuarioJwt = jwtUtil.extraerUsuario(jwt);
        var idCondominio = usuarioJwt.rol() == Rol.ADMINISTRADOR_CONDOMINIO
                ? usuarioJwt.idCondominio() : request.idCondominio();
        var command = new CrearInquilinoCommand(request.nombres(), request.apellidos(),
                request.tipoDocumento(), request.numeroDocumento(), request.idApartamento(),
                idCondominio);
        var result = crearInquilino.ejecutar(command);
        return ResponseEntity.created(URI.create("/api/inquilinos/" + result.id()))
                .body(InquilinoResponse.desdeAplicacion(result));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @GetMapping("/{id}")
    public ResponseEntity<InquilinoResponse> obtener(@PathVariable Long id) {
        var result = obtenerInquilino.ejecutar(id);
        return ResponseEntity.ok(InquilinoResponse.desdeAplicacion(result));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @GetMapping("/documento/{tipoDocumento}/{numeroDocumento}")
    public ResponseEntity<InquilinoResponse> obtenerPorDocumento(
            @PathVariable String tipoDocumento,
            @PathVariable String numeroDocumento) {
        var tipo = com.condominios.sgc.domain.auxiliar.TipoDocumento.valueOf(tipoDocumento);
        var result = obtenerInquilinoPorDocumento.ejecutar(tipo, numeroDocumento);
        return ResponseEntity.ok(InquilinoResponse.desdeAplicacion(result));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @GetMapping
    public ResponseEntity<PaginacionResponse<InquilinoResponse>> listar(
            @RequestParam(defaultValue = "0") @Min(0) int pagina,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int tamano,
            @RequestParam(required = false) String nombres,
            @RequestParam(required = false) String apellidos,
            @RequestParam(required = false) String tipoDocumento,
            @RequestParam(required = false) String numeroDocumento,
            @RequestParam(required = false) Long idApartamento) {
        var query = new ListarInquilinosQuery(pagina, tamano, nombres, apellidos,
                tipoDocumento != null ? com.condominios.sgc.domain.auxiliar.TipoDocumento.valueOf(tipoDocumento) : null,
                numeroDocumento, idApartamento);
        var result = listarInquilinos.ejecutar(query);
        var contenido = result.contenido().stream()
                .map(InquilinoResponse::desdeAplicacion)
                .toList();
        var webResult = new PaginacionResponse<>(contenido, result.pagina(), result.tamano(),
                result.totalElementos(), result.totalPaginas());
        return ResponseEntity.ok(webResult);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @PutMapping("/{id}")
    public ResponseEntity<InquilinoResponse> actualizar(@PathVariable Long id,
            @RequestBody @Valid ActualizarInquilinoRequest request) {
        var command = new ActualizarInquilinoCommand(request.nombres(), request.apellidos(),
                request.tipoDocumento(), request.numeroDocumento(), request.idApartamento(),
                request.desasignarApartamento());
        var result = actualizarInquilino.ejecutar(id, command);
        return ResponseEntity.ok(InquilinoResponse.desdeAplicacion(result));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        eliminarInquilino.ejecutar(id);
        return ResponseEntity.noContent().build();
    }
}
