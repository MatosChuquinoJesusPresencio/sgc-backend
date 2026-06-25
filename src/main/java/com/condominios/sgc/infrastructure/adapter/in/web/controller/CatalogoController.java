package com.condominios.sgc.infrastructure.adapter.in.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.condominios.sgc.application.port.in.CatalogoUseCase;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.CiudadResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.PaisResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.mapper.SuperAdminMapper;

@RestController
@RequestMapping("/api/catalogs")
@PreAuthorize("hasRole('SUPER_ADMINISTRADOR')")
public class CatalogoController {

    private final CatalogoUseCase catalogoUseCase;
    private final SuperAdminMapper mapper;

    public CatalogoController(CatalogoUseCase catalogoUseCase, SuperAdminMapper mapper) {
        this.catalogoUseCase = catalogoUseCase;
        this.mapper = mapper;
    }

    @GetMapping("/countries")
    public ResponseEntity<List<PaisResponse>> listarPaises() {
        var resultados = catalogoUseCase.listarPaises();
        return ResponseEntity.ok(mapper.toPaisResponses(resultados));
    }

    @GetMapping("/countries/{countryId}/cities")
    public ResponseEntity<List<CiudadResponse>> listarCiudades(
            @PathVariable Long countryId) {
        var resultados = catalogoUseCase.listarCiudadesPorPais(countryId);
        return ResponseEntity.ok(mapper.toCiudadResponses(resultados));
    }
}
