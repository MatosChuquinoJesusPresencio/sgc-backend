package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.EliminarInquilinoUseCase;
import com.condominios.sgc.domain.exception.InquilinoException;
import com.condominios.sgc.domain.port.InquilinoPort;

public class EliminarInquilinoUseCaseImpl implements EliminarInquilinoUseCase {

    private final InquilinoPort inquilinoPort;

    public EliminarInquilinoUseCaseImpl(InquilinoPort inquilinoPort) {
        this.inquilinoPort = inquilinoPort;
    }

    @Override
    public void eliminar(Long id) {
        inquilinoPort.findById(id)
                .orElseThrow(() -> InquilinoException.inquilinoNoExistePorId(id));
        inquilinoPort.deleteById(id);
    }
}
