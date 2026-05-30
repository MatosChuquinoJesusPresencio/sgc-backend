package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.ActualizarInquilinoRequest;
import com.condominios.sgc.application.dto.InquilinoResponse;
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
    public InquilinoResponse ejecutar(Long id, ActualizarInquilinoRequest request) {
        InquilinoModel modelo = inquilinoPort.findById(id)
                .orElseThrow(() -> InquilinoException.inquilinoNoExistePorId(id));

        modelo.actualizar(request.nombres(), request.apellidos(), modelo.getDni(), modelo.getApartamentoId());
        InquilinoModel guardado = inquilinoPort.save(modelo);
        return new InquilinoResponse(
                guardado.getId(),
                guardado.getNombres(),
                guardado.getApellidos(),
                guardado.getDni(),
                guardado.getApartamentoId());
    }
}
