package com.condominios.sgc.web.controller;

import java.util.List;
import java.util.Map;

import com.condominios.sgc.application.dto.ActualizarApartamentoRequest;
import com.condominios.sgc.application.dto.CrearApartamentoRequest;
import com.condominios.sgc.application.service.ApartamentoService;
import com.condominios.sgc.application.usecase.ListarApartamentosFiltradosUseCase;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.ApartamentoModel;
import com.condominios.sgc.web.dto.ApartamentoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/apartamentos")
public class ApartamentoController {

    private final ApartamentoService apartamentoService;
    private final ListarApartamentosFiltradosUseCase listarApartamentosFiltradosUseCase;

    public ApartamentoController(ApartamentoService apartamentoService, ListarApartamentosFiltradosUseCase listarApartamentosFiltradosUseCase) {
        this.apartamentoService = apartamentoService;
        this.listarApartamentosFiltradosUseCase = listarApartamentosFiltradosUseCase;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<ApartamentoResponse> crear(@Valid @RequestBody CrearApartamentoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApartamentoResponse.fromModel(apartamentoService.crear(request)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<PaginacionResponse<ApartamentoResponse>> listar(
            @RequestParam Map<String, String> params) {
        var req = PaginacionRequest.desdeParams(params);
        var filtros = req.filtros();
        if (filtros == null || !filtros.containsKey("pisoId")) return ResponseEntity.badRequest().build();
        String pisoVal = filtros.get("pisoId");
        if (pisoVal == null) return ResponseEntity.badRequest().build();
        Long pisoId = Long.valueOf(pisoVal);
        PaginacionResponse<ApartamentoResponse> content = apartamentoService.listarPorPiso(pisoId, req)
                .map(ApartamentoResponse::fromModel);
        return ResponseEntity.ok(content);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApartamentoResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(ApartamentoResponse.fromModel(apartamentoService.obtener(id)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<ApartamentoResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarApartamentoRequest request) {
        return ResponseEntity.ok(ApartamentoResponse.fromModel(apartamentoService.actualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        apartamentoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/apartamentos")
    public ResponseEntity<PaginacionResponse<ApartamentoResponse>> listarApartamentosFiltrados(
            @RequestParam(required = false) Long condominioId,
            @RequestParam(required = false) Long torreId,
            @RequestParam(required = false) Long pisoId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PaginacionRequest req = new PaginacionRequest(page, size, "id", "asc", null);

        PaginacionResponse<ApartamentoModel> pageModel = listarApartamentosFiltradosUseCase.ejecutar(condominioId, torreId, pisoId, req);

        List<ApartamentoResponse> content = pageModel.contenido().stream()
                .map(ApartamentoResponse::fromModel)
                .toList();

        return ResponseEntity.ok(new PaginacionResponse<>(
                content, pageModel.pagina(), pageModel.tamanio(), pageModel.totalElementos(), pageModel.totalPaginas()
        ));
    }
}
