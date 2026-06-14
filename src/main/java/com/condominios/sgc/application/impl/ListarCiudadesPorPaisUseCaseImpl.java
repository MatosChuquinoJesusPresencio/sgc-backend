package com.condominios.sgc.application.impl;

import java.util.List;

import com.condominios.sgc.application.dto.response.CiudadResponse;
import com.condominios.sgc.application.usecase.ListarCiudadesPorPaisUseCase;
import com.condominios.sgc.domain.port.CiudadPort;

public class ListarCiudadesPorPaisUseCaseImpl implements ListarCiudadesPorPaisUseCase {
    private final CiudadPort ciudadPort;

    public ListarCiudadesPorPaisUseCaseImpl(CiudadPort ciudadPort) {
        this.ciudadPort = ciudadPort;
    }

    @Override
    public List<CiudadResponse> ejecutar(Long idPais) {
        return ciudadPort.obtenerPorPais(idPais).stream()
            .map(CiudadResponse::desdeModelo).toList();
    }
}
