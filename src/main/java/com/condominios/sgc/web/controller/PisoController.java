package com.condominios.sgc.web.controller;

import com.condominios.sgc.application.dto.command.CrearPisoCommand;
import com.condominios.sgc.application.dto.query.ListarPisosQuery;
import com.condominios.sgc.application.usecase.piso.ActualizarPisoPorIdUseCase;
import com.condominios.sgc.application.usecase.piso.CrearPisoUseCase;
import com.condominios.sgc.application.usecase.piso.EliminarPisoPorIdUseCase;
import com.condominios.sgc.application.usecase.piso.ListarPisosUseCase;
import com.condominios.sgc.application.usecase.piso.ObtenerPisoPorIdUseCase;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.web.dto.request.ActualizarPisoRequest;
import com.condominios.sgc.web.dto.request.CrearPisoRequest;
import com.condominios.sgc.web.dto.response.PisoResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api/pisos")
@Validated
public class PisoController {

    private final CrearPisoUseCase crearPiso;
    private final ObtenerPisoPorIdUseCase obtenerPiso;
    private final ListarPisosUseCase listarPisos;
    private final ActualizarPisoPorIdUseCase actualizarPiso;
    private final EliminarPisoPorIdUseCase eliminarPiso;

    public PisoController(CrearPisoUseCase crearPiso,
            ObtenerPisoPorIdUseCase obtenerPiso,
            ListarPisosUseCase listarPisos,
            ActualizarPisoPorIdUseCase actualizarPiso,
            EliminarPisoPorIdUseCase eliminarPiso) {
        this.crearPiso = crearPiso;
        this.obtenerPiso = obtenerPiso;
        this.listarPisos = listarPisos;
        this.actualizarPiso = actualizarPiso;
        this.eliminarPiso = eliminarPiso;
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @PostMapping
    public ResponseEntity<PisoResponse> crear(@RequestBody @Valid CrearPisoRequest request) {
        var command = new CrearPisoCommand(request.numero(), request.idTorre());
        var result = crearPiso.ejecutar(command);
        return ResponseEntity.created(URI.create("/api/pisos/" + result.id()))
                .body(PisoResponse.desdeAplicacion(result));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @GetMapping("/{id}")
    public ResponseEntity<PisoResponse> obtener(@PathVariable Long id) {
        var result = obtenerPiso.ejecutar(id);
        return ResponseEntity.ok(PisoResponse.desdeAplicacion(result));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @GetMapping
    public ResponseEntity<PaginacionResponse<PisoResponse>> listar(
            @RequestParam(defaultValue = "0") @Min(0) int pagina,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int tamano,
            @RequestParam(required = false) Integer numero,
            @RequestParam(required = false) Long idTorre) {
        var query = new ListarPisosQuery(pagina, tamano, numero, idTorre);
        var result = listarPisos.ejecutar(query);
        var contenido = result.contenido().stream()
                .map(PisoResponse::desdeAplicacion)
                .toList();
        var webResult = new PaginacionResponse<>(contenido, result.pagina(), result.tamano(),
                result.totalElementos(), result.totalPaginas());
        return ResponseEntity.ok(webResult);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @PutMapping("/{id}")
    public ResponseEntity<PisoResponse> actualizar(@PathVariable Long id,
            @RequestBody @Valid ActualizarPisoRequest request) {
        var result = actualizarPiso.ejecutar(id, request.numero());
        return ResponseEntity.ok(PisoResponse.desdeAplicacion(result));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR', 'ADMINISTRADOR_CONDOMINIO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        eliminarPiso.ejecutar(id);
        return ResponseEntity.noContent().build();
    }
}
