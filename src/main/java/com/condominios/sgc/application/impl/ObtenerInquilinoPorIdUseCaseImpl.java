package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.response.InquilinoResponse;
import com.condominios.sgc.application.usecase.ObtenerInquilinoPorIdUseCase;
import com.condominios.sgc.domain.exception.InquilinoException;
import com.condominios.sgc.domain.port.InquilinoPort;

public class ObtenerInquilinoPorIdUseCaseImpl implements ObtenerInquilinoPorIdUseCase {
    private final InquilinoPort inquilinoPort;

    public ObtenerInquilinoPorIdUseCaseImpl(InquilinoPort inquilinoPort) {
        this.inquilinoPort = inquilinoPort;
    }

    @Override
    public InquilinoResponse ejecutar(Long id) {
        return inquilinoPort.obtenerPorId(id)
            .map(InquilinoResponse::desdeModelo)
            .orElseThrow(InquilinoException::noEncontrado);
    }
}
