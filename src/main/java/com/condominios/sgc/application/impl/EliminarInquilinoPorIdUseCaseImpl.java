package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.EliminarInquilinoPorIdUseCase;
import com.condominios.sgc.domain.exception.InquilinoException;
import com.condominios.sgc.domain.port.InquilinoPort;

public class EliminarInquilinoPorIdUseCaseImpl implements EliminarInquilinoPorIdUseCase {
    private final InquilinoPort inquilinoPort;

    public EliminarInquilinoPorIdUseCaseImpl(InquilinoPort inquilinoPort) {
        this.inquilinoPort = inquilinoPort;
    }

    @Override
    public void ejecutar(Long id) {
        inquilinoPort.obtenerPorId(id)
            .orElseThrow(InquilinoException::noEncontrado);

        inquilinoPort.eliminarPorId(id);
    }
}
