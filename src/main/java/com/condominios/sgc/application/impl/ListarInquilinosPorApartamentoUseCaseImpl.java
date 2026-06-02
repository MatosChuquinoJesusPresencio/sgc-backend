package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.ListarInquilinosPorApartamentoUseCase;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.InquilinoModel;
import com.condominios.sgc.domain.port.InquilinoPort;

public class ListarInquilinosPorApartamentoUseCaseImpl implements ListarInquilinosPorApartamentoUseCase {

    private final InquilinoPort inquilinoPort;

    public ListarInquilinosPorApartamentoUseCaseImpl(InquilinoPort inquilinoPort) {
        this.inquilinoPort = inquilinoPort;
    }

    @Override
    public PaginacionResponse<InquilinoModel> ejecutar(Long apartamentoId, PaginacionRequest request) {
        return inquilinoPort.findByApartamentoId(apartamentoId, request);
    }
}
