package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.InquilinoResponse;
import com.condominios.sgc.application.usecase.ObtenerInquilinoUseCase;
import com.condominios.sgc.domain.exception.InquilinoException;
import com.condominios.sgc.domain.model.InquilinoModel;
import com.condominios.sgc.domain.port.InquilinoPort;

public class ObtenerInquilinoUseCaseImpl implements ObtenerInquilinoUseCase {

    private final InquilinoPort inquilinoPort;

    public ObtenerInquilinoUseCaseImpl(InquilinoPort inquilinoPort) {
        this.inquilinoPort = inquilinoPort;
    }

    @Override
    public InquilinoResponse obtenerPorId(Long id) {
        InquilinoModel modelo = inquilinoPort.findById(id)
                .orElseThrow(() -> InquilinoException.inquilinoNoExistePorId(id));
        return new InquilinoResponse(
                modelo.getId(),
                modelo.getNombres(),
                modelo.getApellidos(),
                modelo.getDni(),
                modelo.getApartamentoId());
    }
}
