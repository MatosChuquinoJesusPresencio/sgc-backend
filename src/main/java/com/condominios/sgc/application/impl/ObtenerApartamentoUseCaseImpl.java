package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.ObtenerApartamentoUseCase;
import com.condominios.sgc.domain.exception.ApartamentoException;
import com.condominios.sgc.domain.model.ApartamentoModel;
import com.condominios.sgc.domain.port.ApartamentoPort;

public class ObtenerApartamentoUseCaseImpl implements ObtenerApartamentoUseCase {
    private final ApartamentoPort apartamentoPort;

    public ObtenerApartamentoUseCaseImpl(ApartamentoPort apartamentoPort) {
        this.apartamentoPort = apartamentoPort;
    }

    @Override
    public ApartamentoModel ejecutar(Long id) {
        ApartamentoModel apartamento = apartamentoPort.findById(id);
        if (apartamento == null) {
            throw ApartamentoException.noEncontrado(id);
        }
        return apartamento;
    }
}