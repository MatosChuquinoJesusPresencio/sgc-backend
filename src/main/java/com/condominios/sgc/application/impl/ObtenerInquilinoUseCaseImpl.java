package com.condominios.sgc.application.impl;

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
    public InquilinoModel ejecutar(Long id) {
        return inquilinoPort.findById(id)
                .orElseThrow(() -> InquilinoException.noEncontrado(id));
    }
}
