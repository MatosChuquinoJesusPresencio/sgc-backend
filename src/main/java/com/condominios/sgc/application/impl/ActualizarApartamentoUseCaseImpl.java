package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.ActualizarApartamentoRequest;
import com.condominios.sgc.application.usecase.ActualizarApartamentoUseCase;
import com.condominios.sgc.domain.exception.ApartamentoException;
import com.condominios.sgc.domain.model.ApartamentoModel;
import com.condominios.sgc.domain.port.ApartamentoPort;

public class ActualizarApartamentoUseCaseImpl implements ActualizarApartamentoUseCase {
    private final ApartamentoPort apartamentoPort;

    public ActualizarApartamentoUseCaseImpl(ApartamentoPort apartamentoPort) {
        this.apartamentoPort = apartamentoPort;
    }

    @Override
    public ApartamentoModel ejecutar(Long id, ActualizarApartamentoRequest request) {
        ApartamentoModel apartamento = apartamentoPort.findById(id)
                .orElseThrow(() -> ApartamentoException.noEncontrado(id));

        apartamento.actualizarDatos(request.numero(),
                request.derechoEstacionamiento(),
                request.metraje());

        return apartamentoPort.save(apartamento);
    }
}