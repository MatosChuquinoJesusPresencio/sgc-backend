package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.ActualizarInquilinoRequest;
import com.condominios.sgc.application.usecase.ActualizarInquilinoUseCase;
import com.condominios.sgc.domain.exception.InquilinoException;
import com.condominios.sgc.domain.model.InquilinoModel;
import com.condominios.sgc.domain.port.InquilinoPort;

public class ActualizarInquilinoUseCaseImpl implements ActualizarInquilinoUseCase {

    private final InquilinoPort inquilinoPort;

    public ActualizarInquilinoUseCaseImpl(InquilinoPort inquilinoPort) {
        this.inquilinoPort = inquilinoPort;
    }

    @Override
    public InquilinoModel ejecutar(Long id, ActualizarInquilinoRequest request) {
        InquilinoModel modelo = inquilinoPort.findById(id)
                .orElseThrow(() -> InquilinoException.noEncontrado(id));
        modelo.actualizarDatos(request.nombres(), request.apellidos());
        return inquilinoPort.save(modelo);
    }
}
