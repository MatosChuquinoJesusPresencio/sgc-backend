package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.ListarApartamentosFiltradosUseCase;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.ApartamentoModel;
import com.condominios.sgc.domain.port.ApartamentoPort;

public class ListarApartamentosFiltradosUseCaseImpl implements ListarApartamentosFiltradosUseCase {
    private final ApartamentoPort apartamentoPort;

    public ListarApartamentosFiltradosUseCaseImpl(ApartamentoPort apartamentoPort) {
        this.apartamentoPort = apartamentoPort;
    }

    @Override
    public PaginacionResponse<ApartamentoModel> ejecutar(Long condominioId, Long torreId, Long pisoId, PaginacionRequest request) {
        return apartamentoPort.findByFiltros(condominioId, torreId, pisoId, request);
    }
}