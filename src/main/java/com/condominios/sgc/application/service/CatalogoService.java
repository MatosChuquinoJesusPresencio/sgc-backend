package com.condominios.sgc.application.service;

import java.util.List;

import com.condominios.sgc.application.dto.result.CiudadResult;
import com.condominios.sgc.application.dto.result.PaisResult;
import com.condominios.sgc.application.port.in.CatalogoUseCase;
import com.condominios.sgc.application.port.out.CiudadRepositoryPort;
import com.condominios.sgc.application.port.out.PaisRepositoryPort;

public class CatalogoService implements CatalogoUseCase {

    private final PaisRepositoryPort paisRepository;
    private final CiudadRepositoryPort ciudadRepository;

    public CatalogoService(PaisRepositoryPort paisRepository,
                           CiudadRepositoryPort ciudadRepository) {
        this.paisRepository = paisRepository;
        this.ciudadRepository = ciudadRepository;
    }

    @Override
    public List<PaisResult> listarPaises() {
        return paisRepository.listarTodos().stream()
            .map(p -> new PaisResult(p.id(), p.nombre(), p.codigoIso()))
            .toList();
    }

    @Override
    public List<CiudadResult> listarCiudadesPorPais(Long paisId) {
        return ciudadRepository.buscarPorPaisId(paisId).stream()
            .map(c -> new CiudadResult(c.id(), c.nombre(), c.idPais()))
            .toList();
    }
}
