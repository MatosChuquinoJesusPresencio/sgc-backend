package com.condominios.sgc.application.impl;

import java.util.List;

import com.condominios.sgc.application.dto.response.PaisResponse;
import com.condominios.sgc.application.usecase.ListarPaisesUseCase;
import com.condominios.sgc.domain.port.PaisPort;

public class ListarPaisesUseCaseImpl implements ListarPaisesUseCase {
    private final PaisPort paisPort;

    public ListarPaisesUseCaseImpl(PaisPort paisPort) {
        this.paisPort = paisPort;
    }

    @Override
    public List<PaisResponse> ejecutar() {
        return paisPort.obtenerTodos().stream()
            .map(PaisResponse::desdeModelo).toList();
    }
}
