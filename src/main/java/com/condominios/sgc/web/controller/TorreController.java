package com.condominios.sgc.web.controller;


import com.condominios.sgc.application.dto.ActualizarTorreRequest;
import com.condominios.sgc.application.dto.CrearTorreRequest;
import com.condominios.sgc.application.usecase.*;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.TorreModel;
import com.condominios.sgc.web.dto.TorreResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TorreController {
    private final CrearTorreUseCase crearTorreUseCase;
    private final ObtenerTorreUseCase obtenerTorreUseCase;
    private final ListarTorresPorCondominioUseCase listarTorresPorCondominioUseCase;
    private final ActualizarTorreUseCase actualizarTorreUseCase;
    private final EliminarTorreUseCase eliminarTorreUseCase;

    public TorreController(CrearTorreUseCase crearTorreUseCase, ObtenerTorreUseCase obtenerTorreUseCase,
                           ListarTorresPorCondominioUseCase listarTorresPorCondominioUseCase,
                           ActualizarTorreUseCase actualizarTorreUseCase, EliminarTorreUseCase eliminarTorreUseCase) {
        this.crearTorreUseCase = crearTorreUseCase;
        this.obtenerTorreUseCase = obtenerTorreUseCase;
        this.listarTorresPorCondominioUseCase = listarTorresPorCondominioUseCase;
        this.actualizarTorreUseCase = actualizarTorreUseCase;
        this.eliminarTorreUseCase = eliminarTorreUseCase;
    }

    @PostMapping("/condominios/{condominioId}/torres")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<TorreResponse> crearTorre(
            @PathVariable Long condominioId,
            @RequestBody CrearTorreRequest request) {
        CrearTorreRequest reqCompleto = new CrearTorreRequest(request.nombre(), condominioId);
        TorreModel torre = crearTorreUseCase.ejecutar(reqCompleto);
        return ResponseEntity.status(HttpStatus.CREATED).body(TorreResponse.fromModel(torre));
    }

    @GetMapping("/condominios/{condominioId}/torres")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PaginacionResponse<TorreResponse>> listarTorresPorCondominio(
            @PathVariable Long condominioId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PaginacionRequest req = new PaginacionRequest(page, size, "id", "asc", null);

        PaginacionResponse<TorreModel> pageModel = listarTorresPorCondominioUseCase.ejecutar(condominioId, req);

        List<TorreResponse> content = pageModel.contenido().stream().map(TorreResponse::fromModel).toList();

        return ResponseEntity.ok(new PaginacionResponse<>(
                content,
                pageModel.pagina(),
                pageModel.tamanio(),
                pageModel.totalElementos(),
                pageModel.totalPaginas()
        ));
    }

    @GetMapping("/torres/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TorreResponse> obtenerTorre(@PathVariable Long id) {
        return ResponseEntity.ok(TorreResponse.fromModel(obtenerTorreUseCase.ejecutar(id)));
    }

    @PutMapping("/torres/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<TorreResponse> actualizarTorre(
            @PathVariable Long id,
            @RequestBody ActualizarTorreRequest request) {
        return ResponseEntity.ok(TorreResponse.fromModel(actualizarTorreUseCase.ejecutar(id, request)));
    }

    @DeleteMapping("/torres/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<Void> eliminarTorre(@PathVariable Long id) {
        eliminarTorreUseCase.ejecutar(id);
        return ResponseEntity.noContent().build();
    }
}
