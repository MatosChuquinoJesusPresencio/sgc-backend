package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.CrearApartamentoRequest;
import com.condominios.sgc.application.usecase.CrearApartamentoUseCase;
import com.condominios.sgc.domain.exception.PisoException;
import com.condominios.sgc.domain.model.ApartamentoModel;
import com.condominios.sgc.domain.model.PisoModel;
import com.condominios.sgc.domain.port.ApartamentoPort;
import com.condominios.sgc.domain.port.PisoPort;

public class CrearApartamentoUseCaseImpl implements CrearApartamentoUseCase {
    private final ApartamentoPort apartamentoPort;
    private final PisoPort pisoPort;

    public CrearApartamentoUseCaseImpl(ApartamentoPort apartamentoPort, PisoPort pisoPort) {
        this.apartamentoPort = apartamentoPort;
        this.pisoPort = pisoPort;
    }

    @Override
    public ApartamentoModel ejecutar(CrearApartamentoRequest request) {
        PisoModel piso = pisoPort.findById(request.pisoId())
            .orElseThrow(() -> PisoException.noEncontrado(request.pisoId()));

        ApartamentoModel apartamento = new ApartamentoModel(
                request.numero(),
                request.derechoEstacionamiento(),
                request.metraje(),
                piso.getId()
        );

        return apartamentoPort.save(apartamento);
    }
}
