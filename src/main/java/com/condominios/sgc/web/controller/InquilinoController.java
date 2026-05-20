package com.condominios.sgc.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.condominios.sgc.application.dto.CrearInquilinoRequest;
import com.condominios.sgc.application.dto.InquilinoResponse;
import com.condominios.sgc.application.usecase.CrearInquilinoUseCase;
import com.condominios.sgc.application.usecase.EliminarInquilinoUseCase;
import com.condominios.sgc.application.usecase.ListarInquilinosPorApartamentoUseCase;
import com.condominios.sgc.application.usecase.ObtenerInquilinoUseCase;

@RestController
@RequestMapping("/api")
public class InquilinoController {

    private final CrearInquilinoUseCase crearUseCase;
    private final ObtenerInquilinoUseCase obtenerUseCase;
    private final ListarInquilinosPorApartamentoUseCase listarUseCase;
    private final EliminarInquilinoUseCase eliminarUseCase;

    public InquilinoController(
            CrearInquilinoUseCase crearUseCase,
            ObtenerInquilinoUseCase obtenerUseCase,
            ListarInquilinosPorApartamentoUseCase listarUseCase,
            EliminarInquilinoUseCase eliminarUseCase) {
        this.crearUseCase = crearUseCase;
        this.obtenerUseCase = obtenerUseCase;
        this.listarUseCase = listarUseCase;
        this.eliminarUseCase = eliminarUseCase;
    }

    @PostMapping("/apartamentos/{apartamentoId}/inquilinos")
    public ResponseEntity<InquilinoResponse> crearInquilino(
            @PathVariable Long apartamentoId,
            @RequestBody CrearInquilinoRequest request) {
        request.setApartamentoId(apartamentoId);
        InquilinoResponse response = crearUseCase.crear(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/apartamentos/{apartamentoId}/inquilinos")
    public ResponseEntity<List<InquilinoResponse>> listarInquilinos(@PathVariable Long apartamentoId) {
        return ResponseEntity.ok(listarUseCase.listarPorApartamentoId(apartamentoId));
    }

    @GetMapping("/inquilinos/{id}")
    public ResponseEntity<InquilinoResponse> obtenerInquilino(@PathVariable Long id) {
        return ResponseEntity.ok(obtenerUseCase.obtenerPorId(id));
    }

    @DeleteMapping("/inquilinos/{id}")
    public ResponseEntity<Void> eliminarInquilino(@PathVariable Long id) {
        eliminarUseCase.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
