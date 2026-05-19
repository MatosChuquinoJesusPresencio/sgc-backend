package com.condominios.sgc.web.controller;

import com.condominios.sgc.application.dto.ActualizarPisoRequest;
import com.condominios.sgc.application.dto.CrearPisoRequest;
import com.condominios.sgc.application.usecase.*;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.PisoModel;
import com.condominios.sgc.web.dto.PisoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PisoController {
    private final CrearPisoUseCase crearPisoUseCase;
    private final ObtenerPisoUseCase obtenerPisoUseCase;
    private final ListarPisosPorTorreUseCase listarPisosPorTorreUseCase;
    private final ActualizarPisoUseCase actualizarPisoUseCase;
    private final EliminarPisoUseCase eliminarPisoUseCase;

    public PisoController(CrearPisoUseCase crearPisoUseCase, ObtenerPisoUseCase obtenerPisoUseCase,
                          ListarPisosPorTorreUseCase listarPisosPorTorreUseCase,
                          ActualizarPisoUseCase actualizarPisoUseCase, EliminarPisoUseCase eliminarPisoUseCase) {
        this.crearPisoUseCase = crearPisoUseCase;
        this.obtenerPisoUseCase = obtenerPisoUseCase;
        this.listarPisosPorTorreUseCase = listarPisosPorTorreUseCase;
        this.actualizarPisoUseCase = actualizarPisoUseCase;
        this.eliminarPisoUseCase = eliminarPisoUseCase;
    }

    @PostMapping("/torres/{torreId}/pisos")
    public ResponseEntity<PisoResponse> crearPiso(
            @PathVariable Long torreId,
            @RequestBody CrearPisoRequest request) {
        CrearPisoRequest reqCompleto = new CrearPisoRequest(request.numero(), torreId);
        PisoModel piso = crearPisoUseCase.ejecutar(reqCompleto);
        return ResponseEntity.status(HttpStatus.CREATED).body(PisoResponse.fromModel(piso));
    }

    @GetMapping("/torres/{torreId}/pisos")
    public ResponseEntity<PaginacionResponse<PisoResponse>> listarPisosPorTorre(
            @PathVariable Long torreId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PaginacionRequest req = new PaginacionRequest(page, size, "id", "asc", null);
        PaginacionResponse<PisoModel> pageModel = listarPisosPorTorreUseCase.ejecutar(torreId, req);

        List<PisoResponse> content = pageModel.contenido().stream().map(PisoResponse::fromModel).toList();

        return ResponseEntity.ok(new PaginacionResponse<>(
                content,
                pageModel.pagina(),
                pageModel.tamanio(),
                pageModel.totalElementos(),
                pageModel.totalPaginas()
        ));
    }

    @GetMapping("/pisos/{id}")
    public ResponseEntity<PisoResponse> obtenerPiso(@PathVariable Long id) {
        return ResponseEntity.ok(PisoResponse.fromModel(obtenerPisoUseCase.ejecutar(id)));
    }

    @PutMapping("/pisos/{id}")
    public ResponseEntity<PisoResponse> actualizarPiso(
            @PathVariable Long id,
            @RequestBody ActualizarPisoRequest request) {
        return ResponseEntity.ok(PisoResponse.fromModel(actualizarPisoUseCase.ejecutar(id, request)));
    }

    @DeleteMapping("/pisos/{id}")
    public ResponseEntity<Void> eliminarPiso(@PathVariable Long id) {
        eliminarPisoUseCase.ejecutar(id);
        return ResponseEntity.noContent().build();
    }
}