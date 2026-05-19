package com.condominios.sgc.web.controller;

import com.condominios.sgc.application.dto.ActualizarApartamentoRequest;
import com.condominios.sgc.application.dto.CrearApartamentoRequest;
import com.condominios.sgc.application.usecase.*;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.ApartamentoModel;
import com.condominios.sgc.web.dto.ApartamentoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApartamentoController {
    private final CrearApartamentoUseCase crearApartamentoUseCase;
    private final ObtenerApartamentoUseCase obtenerApartamentoUseCase;
    private final ListarApartamentosPorPisoUseCase listarApartamentosPorPisoUseCase;
    private final ActualizarApartamentoUseCase actualizarApartamentoUseCase;
    private final EliminarApartamentoUseCase eliminarApartamentoUseCase;

    public ApartamentoController(CrearApartamentoUseCase crearApartamentoUseCase, ObtenerApartamentoUseCase obtenerApartamentoUseCase,
                                 ListarApartamentosPorPisoUseCase listarApartamentosPorPisoUseCase,
                                 ActualizarApartamentoUseCase actualizarApartamentoUseCase, EliminarApartamentoUseCase eliminarApartamentoUseCase) {
        this.crearApartamentoUseCase = crearApartamentoUseCase;
        this.obtenerApartamentoUseCase = obtenerApartamentoUseCase;
        this.listarApartamentosPorPisoUseCase = listarApartamentosPorPisoUseCase;
        this.actualizarApartamentoUseCase = actualizarApartamentoUseCase;
        this.eliminarApartamentoUseCase = eliminarApartamentoUseCase;
    }

    @PostMapping("/pisos/{pisoId}/apartamentos")
    public ResponseEntity<ApartamentoResponse> crearApartamento(
            @PathVariable Long pisoId,
            @RequestBody CrearApartamentoRequest request) {
        CrearApartamentoRequest reqCompleto = new CrearApartamentoRequest(request.numero(), request.derechoEstacionamiento(), request.metraje(), pisoId, request.propietarioId());
        ApartamentoModel apartamento = crearApartamentoUseCase.ejecutar(reqCompleto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApartamentoResponse.fromModel(apartamento));
    }

    @GetMapping("/pisos/{pisoId}/apartamentos")
    public ResponseEntity<PaginacionResponse<ApartamentoResponse>> listarApartamentosPorPiso(
            @PathVariable Long pisoId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PaginacionRequest req = new PaginacionRequest(page, size, "id", "asc", null);
        PaginacionResponse<ApartamentoModel> pageModel = listarApartamentosPorPisoUseCase.ejecutar(pisoId, req);

        List<ApartamentoResponse> content = pageModel.contenido().stream().map(ApartamentoResponse::fromModel).toList();

        return ResponseEntity.ok(new PaginacionResponse<>(
                content,
                pageModel.pagina(),
                pageModel.tamanio(),
                pageModel.totalElementos(),
                pageModel.totalPaginas()
        ));
    }

    @GetMapping("/apartamentos/{id}")
    public ResponseEntity<ApartamentoResponse> obtenerApartamento(@PathVariable Long id) {
        return ResponseEntity.ok(ApartamentoResponse.fromModel(obtenerApartamentoUseCase.ejecutar(id)));
    }

    @PutMapping("/apartamentos/{id}")
    public ResponseEntity<ApartamentoResponse> actualizarApartamento(
            @PathVariable Long id,
            @RequestBody ActualizarApartamentoRequest request) {
        return ResponseEntity.ok(ApartamentoResponse.fromModel(actualizarApartamentoUseCase.ejecutar(id, request)));
    }

    @DeleteMapping("/apartamentos/{id}")
    public ResponseEntity<Void> eliminarApartamento(@PathVariable Long id) {
        eliminarApartamentoUseCase.ejecutar(id);
        return ResponseEntity.noContent().build();
    }
}