package com.condominios.sgc.application.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.condominios.sgc.application.dto.InquilinoResponse;
import com.condominios.sgc.application.usecase.ListarInquilinosPorApartamentoUseCase;
import com.condominios.sgc.domain.model.InquilinoModel;
import com.condominios.sgc.domain.port.InquilinoPort;

public class ListarInquilinosPorApartamentoUseCaseImpl implements ListarInquilinosPorApartamentoUseCase {

    private final InquilinoPort inquilinoPort;

    public ListarInquilinosPorApartamentoUseCaseImpl(InquilinoPort inquilinoPort) {
        this.inquilinoPort = inquilinoPort;
    }

    @Override
    public List<InquilinoResponse> listarPorApartamentoId(Long apartamentoId) {
        return inquilinoPort.findByApartamentoId(apartamentoId)
                .stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    private InquilinoResponse mapearAResponse(InquilinoModel modelo) {
        return new InquilinoResponse(
                modelo.getId(),
                modelo.getNombres(),
                modelo.getApellidos(),
                modelo.getDni(),
                modelo.getApartamentoId());
    }
}
