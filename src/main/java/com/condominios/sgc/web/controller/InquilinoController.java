package com.condominios.sgc.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.condominios.sgc.application.dto.ActualizarInquilinoRequest;
import com.condominios.sgc.application.dto.CrearInquilinoRequest;
import com.condominios.sgc.application.usecase.ActualizarInquilinoUseCase;
import com.condominios.sgc.application.usecase.CrearInquilinoUseCase;
import com.condominios.sgc.application.usecase.EliminarInquilinoUseCase;
import com.condominios.sgc.application.usecase.ListarInquilinosPorApartamentoUseCase;
import com.condominios.sgc.application.usecase.ObtenerInquilinoUseCase;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.web.dto.InquilinoResponse;

@RestController
@RequestMapping("/api")
public class InquilinoController {

    private final CrearInquilinoUseCase crearUseCase;
    private final ObtenerInquilinoUseCase obtenerUseCase;
    private final ListarInquilinosPorApartamentoUseCase listarUseCase;
    private final ActualizarInquilinoUseCase actualizarUseCase;
    private final EliminarInquilinoUseCase eliminarUseCase;

    public InquilinoController(
            CrearInquilinoUseCase crearUseCase,
            ObtenerInquilinoUseCase obtenerUseCase,
            ListarInquilinosPorApartamentoUseCase listarUseCase,
            ActualizarInquilinoUseCase actualizarUseCase,
            EliminarInquilinoUseCase eliminarUseCase) {
        this.crearUseCase = crearUseCase;
        this.obtenerUseCase = obtenerUseCase;
        this.listarUseCase = listarUseCase;
        this.actualizarUseCase = actualizarUseCase;
        this.eliminarUseCase = eliminarUseCase;
    }

    @PostMapping("/apartamentos/{apartamentoId}/inquilinos")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<InquilinoResponse> crearInquilino(
            @PathVariable Long apartamentoId,
            @RequestBody CrearInquilinoRequest request) {
        var reqCompleto = new CrearInquilinoRequest(request.nombres(), request.apellidos(), request.dni(), apartamentoId);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(InquilinoResponse.fromModel(crearUseCase.ejecutar(reqCompleto)));
    }

    @GetMapping("/apartamentos/{apartamentoId}/inquilinos")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PaginacionResponse<InquilinoResponse>> listarInquilinos(
            @PathVariable Long apartamentoId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var req = new PaginacionRequest(page, size, "id", "asc", null);
        var pageModel = listarUseCase.ejecutar(apartamentoId, req);
        var content = pageModel.contenido().stream().map(InquilinoResponse::fromModel).toList();
        return ResponseEntity.ok(new PaginacionResponse<>(
            content, pageModel.pagina(), pageModel.tamanio(),
            pageModel.totalElementos(), pageModel.totalPaginas()));
    }

    @GetMapping("/inquilinos/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<InquilinoResponse> obtenerInquilino(@PathVariable Long id) {
        return ResponseEntity.ok(InquilinoResponse.fromModel(obtenerUseCase.ejecutar(id)));
    }

    @PutMapping("/inquilinos/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<InquilinoResponse> actualizarInquilino(
            @PathVariable Long id,
            @RequestBody ActualizarInquilinoRequest request) {
        return ResponseEntity.ok(InquilinoResponse.fromModel(actualizarUseCase.ejecutar(id, request)));
    }

    @DeleteMapping("/inquilinos/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMINISTRADOR','ADMINISTRADOR_CONDOMINIO')")
    public ResponseEntity<Void> eliminarInquilino(@PathVariable Long id) {
        eliminarUseCase.ejecutar(id);
        return ResponseEntity.noContent().build();
    }
}
