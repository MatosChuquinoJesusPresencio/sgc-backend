package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.EliminarApartamentoUseCase;
import com.condominios.sgc.domain.exception.ApartamentoException;
import com.condominios.sgc.domain.port.ApartamentoPort;

public class EliminarApartamentoUseCaseImpl implements EliminarApartamentoUseCase {
    private final ApartamentoPort apartamentoPort;

    public EliminarApartamentoUseCaseImpl(ApartamentoPort apartamentoPort) {
        this.apartamentoPort = apartamentoPort;
    }

    @Override
    public void ejecutar(Long id) {
        apartamentoPort.findById(id)
                .orElseThrow(() -> ApartamentoException.noEncontrado(id));
        apartamentoPort.deleteById(id);
    }
}